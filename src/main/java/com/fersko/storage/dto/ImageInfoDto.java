package com.fersko.storage.dto;

import java.time.LocalDateTime;

public record ImageInfoDto(
		Long id,
		String username,
		String name,
		LocalDateTime uploadedTime,
		String url
) {
}
