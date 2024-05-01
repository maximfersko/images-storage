package com.fersko.storage.service;

import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
	Image save(MultipartFile image, User user) throws IOException;

	void deleteById(Long id);

	Image findImageById(Long id);

}
