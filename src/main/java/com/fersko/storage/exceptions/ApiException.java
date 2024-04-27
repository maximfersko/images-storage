package com.fersko.storage.exceptions;

import lombok.Getter;

public class ApiException extends RuntimeException {
	@Getter
	protected final String errorCode;

	public ApiException(String message, String code) {
		super(message);
		this.errorCode = code;
	}
}
