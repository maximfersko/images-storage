package com.fersko.storage.controller;

import com.fersko.storage.dto.UserDto;
import com.fersko.storage.mapper.UserMapper;
import com.fersko.storage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/storage")
public class UserController {
	private final UserService userService;
private final UserMapper userMapper;

	@GetMapping("/user/info")
	public ResponseEntity<UserDto> getCurrentUserInfo(Authentication authentication) {
		String name = authentication.getName();
		UserDto userDto = userMapper.toDto(userService.findByUsername(name));
		return ResponseEntity.ok(userDto);
	}
}
