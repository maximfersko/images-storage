package com.fersko.storage.security;

import com.fersko.storage.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String HEADER_NAME = "Authorization";

	private final SecurityService securityService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader(HEADER_NAME);

		if (StringUtils.isBlank(header) || !header.startsWith(BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(BEARER_PREFIX.length());
		processToken(token, request);
		filterChain.doFilter(request, response);
	}

	private void processToken(String token, HttpServletRequest request) {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			String username = securityService.extractUsername(token);
			if (StringUtils.isNotBlank(username)) {
				UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
				if (securityService.isValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
	}
}
