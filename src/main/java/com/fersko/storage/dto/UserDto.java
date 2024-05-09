package com.fersko.storage.dto;

import com.fersko.storage.entity.Role;
import lombok.Builder;

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
