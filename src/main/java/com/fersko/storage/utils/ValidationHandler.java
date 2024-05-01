package com.fersko.storage.utils;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ValidationHandler {
	private ValidationHandler() {

	}

	public static String extractErrors(BindingResult bindingResult) {
		List<String> errors = new ArrayList<>(bindingResult.getErrorCount());
		for (var error : bindingResult.getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		return "Validation Failed: \n" + String.join("\n", errors);
	}
}