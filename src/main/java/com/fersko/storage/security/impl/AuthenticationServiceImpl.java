package com.fersko.storage.security.impl;

import com.fersko.storage.dto.SignInRequest;
import com.fersko.storage.dto.SignUpRequest;
import com.fersko.storage.entity.Role;
import com.fersko.storage.entity.Token;
import com.fersko.storage.entity.User;
import com.fersko.storage.exceptions.AuthenticateException;
import com.fersko.storage.security.AuthenticationService;
import com.fersko.storage.security.SecurityService;
import com.fersko.storage.security.TokenDetails;
import com.fersko.storage.service.TokenService;
import com.fersko.storage.service.UserService;
import com.fersko.storage.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserService userService;
	private final SecurityService securityService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final UserServiceImpl userServiceImpl;

	@Transactional
	@Override
	public TokenDetails signIn(SignInRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);
		if (authentication.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			TokenDetails token = securityService.generateToken(userDetails);

			User user = userServiceImpl.findByUsername(request.username());
			Token tokenEntity = Token.builder()
					.token(token.getToken())
					.loggedOut(false)
					.user(user)
					.build();

			revokeAllTokenByUser(user);
			tokenService.save(tokenEntity);
			return token;
		} else {
			throw new AuthenticateException("Incorrect login or password", "INCORRECT_AUTHENTICATE_SIGNIN");
		}
	}

	private void revokeAllTokenByUser(User user) {
		List<Token> validTokens = tokenService.findAllByTokenByUserId(user.getId());
		log.info(validTokens.toString());
		if (!validTokens.isEmpty()) {
			validTokens.forEach(validToken -> validToken.setLoggedOut(true));
			tokenService.saveAll(validTokens);
		}
	}

	@Transactional
	@Override
	public User signUp(SignUpRequest request) {
		if (userService.existsUserByUsername(request.username())) {
			throw new AuthenticateException("The login already exists", "DUPLICATE_LOGIN_SIGNUP");
		}
		if (userService.existsUserByEmail(request.email())) {
			throw new AuthenticateException("The email already exists", "DUPLICATE_LOGIN_EMAIL");
		}
		User user = User.builder()
				.role(Role.USER)
				.firstname(request.firstname())
				.email(request.email())
				.active(true)
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.createdAt(LocalDateTime.now())
				.build();

		return userService.save(user);
	}


}
