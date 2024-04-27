package com.fersko.storage.security.impl;

import com.fersko.storage.dto.SignInRequest;
import com.fersko.storage.dto.SignUpRequest;
import com.fersko.storage.entity.Role;
import com.fersko.storage.entity.User;
import com.fersko.storage.security.AuthenticationService;
import com.fersko.storage.security.SecurityService;
import com.fersko.storage.security.TokenDetails;
import com.fersko.storage.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserService userService;
	private final SecurityService securityService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	@Override
	public TokenDetails signIn(SignInRequest request) {
		log.info("SignIn request received {}", request);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);
		log.info("SignIn completed");

		if (authentication.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			log.info("User found: " + userDetails.getUsername());
			return securityService.generateToken(userDetails);
		} else {
			throw new RuntimeException("");
		}
	}

	@Override
	public User signUp(SignUpRequest request) {
		User user = User.builder()
				.role(Role.USER)
				.active(true)
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.createdAt(LocalDateTime.now())
				.build();

		return userService.save(user);
	}


}
