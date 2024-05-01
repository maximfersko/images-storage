package com.fersko.storage.config;

import com.fersko.storage.consts.AuthConsts;
import com.fersko.storage.entity.Token;
import com.fersko.storage.exceptions.NotFoundTokenException;
import com.fersko.storage.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomLogouHandler implements LogoutHandler {
	private final TokenService tokenService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String header = request.getHeader(AuthConsts.HEADER_NAME);

		if (StringUtils.isBlank(header) || !header.startsWith(AuthConsts.BEARER_PREFIX)) {
			return;
		}

		String token = header.substring(AuthConsts.BEARER_PREFIX.length());

		Token tokenStorage = tokenService.findByToken(token)
				.orElseThrow(() -> new NotFoundTokenException("Token not found"));

		tokenStorage.setLoggedOut(true);
		tokenService.save(tokenStorage);
	}
}
