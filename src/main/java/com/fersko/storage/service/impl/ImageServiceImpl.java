package com.fersko.storage.service.impl;

import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.User;
import com.fersko.storage.exceptions.ImageNotFoundException;
import com.fersko.storage.repository.ImageRepository;
import com.fersko.storage.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
	private final ImageRepository imageRepository;

	@Override
	public Image save(MultipartFile image, User user) throws IOException {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		Image data = Image.builder()
				.name(fileName)
				.user(user)
				.uploadedTime(LocalDateTime.now())
				.data(image.getBytes())
				.build();
		return imageRepository.save(data);
	}

	@Override
	public void deleteById(Long id) {
		if (!imageRepository.existsById(id)) {
			throw new ImageNotFoundException(id);
		}
		imageRepository.deleteById(id);
	}


	@Override
	public Image findImageById(Long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
	}
}
