package com.fersko.storage.controller;

import com.fersko.storage.dto.SignInRequest;
import com.fersko.storage.dto.SignUpRequest;
import com.fersko.storage.dto.TokenDetails;
import com.fersko.storage.dto.UserDto;
import com.fersko.storage.mapper.UserMapper;
import com.fersko.storage.service.AuthenticationService;
import com.fersko.storage.utils.ValidationHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
	private final UserMapper userMapper;

	@PostMapping("sign-up")
	public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationHandler.extractErrors(bindingResult));
		} else {
			UserDto userDto = userMapper.toDto(authenticationService.signUp(request));
			return ResponseEntity.ok(userDto);
		}
	}

	@PostMapping("sign-in")
	public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationHandler.extractErrors(bindingResult));
		} else {
			TokenDetails tokenDetails = authenticationService.signIn(request);
			return ResponseEntity.ok(tokenDetails);
		}
	}
}
