package com.fersko.storage.dto;

import com.fersko.storage.entity.Role;
import lombok.Builder;

import java.util.Date;

@Builder(toBuilder = true)
public record TokenDetails(
		Long id,
		Role role,
		String token,
		Date issuedAt,
		Date createdAt
) {
}
