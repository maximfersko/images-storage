package com.fersko.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fersko.storage.entity.Role;

public record UserDto(
		Long id,
		String username,
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		String password,
		boolean active,
		Role role
) {
}
