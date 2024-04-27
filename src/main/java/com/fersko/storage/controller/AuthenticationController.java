package com.fersko.storage.controller;

import com.fersko.storage.dto.SignInRequest;
import com.fersko.storage.dto.SignUpRequest;
import com.fersko.storage.security.AuthenticationService;
import com.fersko.storage.security.TokenDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
		log.info("Sign up request received: {}", request);
		return ResponseEntity.ok(authenticationService.signUp(request));
	}

	@PostMapping("sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
		TokenDetails tokenDetails = authenticationService.signIn(request);
		log.info("Sign in received: {}", tokenDetails);
		return ResponseEntity.ok(tokenDetails);
	}
}
