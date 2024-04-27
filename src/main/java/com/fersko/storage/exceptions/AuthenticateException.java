package com.fersko.storage.exceptions;

public class AuthenticateException extends ApiException {
	public AuthenticateException(String message, String errorCode) {
		super(message, errorCode);
	}
}
