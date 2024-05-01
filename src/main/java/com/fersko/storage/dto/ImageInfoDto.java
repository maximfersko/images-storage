package com.fersko.storage.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ImageInfoDto(
		Long id,
		String username,
		String name,
		LocalDateTime uploadedTime,
		String url
) implements Serializable {
}
