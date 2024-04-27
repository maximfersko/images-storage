package com.fersko.storage.exceptions;

public class ImageNotFoundException extends ApiException {
	public ImageNotFoundException(Long id) {
		super("Image with Id" + id + "not found", "IMAGE_NOT_FOUND");
	}
}
