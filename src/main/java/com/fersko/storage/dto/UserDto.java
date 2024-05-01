package com.fersko.storage.dto;

import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

public record UserDto(
		Long id,
		String username,
		String firstname,
		String email,
		boolean active,
		List<Image> images,
		LocalDateTime createdAt,
		Role role
) {
}
