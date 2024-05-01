package com.fersko.storage.dto;

public record SignUpRequest(
		String username,
		String firstname,
		String email,
		String password
) {
}
