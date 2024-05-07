package com.fersko.storage.dto;

import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.Role;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserDto(
		Long id,
		String username,
		String firstname,
		String email,
		boolean active,
		Role role
) {
}
