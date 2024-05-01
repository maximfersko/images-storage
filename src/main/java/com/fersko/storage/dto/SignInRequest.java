package com.fersko.storage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SignInRequest(
		@NotEmpty
		@Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
		String username,
		@NotEmpty
		@Size(min = 8, max = 255, message = "Length must be between 8 and 255 characters")
		String password
) {
}
