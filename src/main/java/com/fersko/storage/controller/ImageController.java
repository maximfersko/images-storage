package com.fersko.storage.controller;

import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.User;
import com.fersko.storage.service.ImageService;
import com.fersko.storage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/storage")
public class ImageController {
	private final ImageService imageService;
	private final UserService userService;

	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User user = userService.findByUsername(username);
		if (user != null && !file.isEmpty()) {
			try {
				imageService.save(file, user);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return"redirect:/storage";
	}


	@GetMapping("/image")
	@ResponseBody
	public List<ImageInfoDto> getAllImages(@RequestParam("page") int page) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userService.extractInfo(username, PageRequest.of(page, 10));
	}

@GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
public ResponseEntity<byte[]> getImageById(@PathVariable("id") Long id) {
	byte[] imageData = imageService.findImageById(id).getData();
	if (imageData != null) {
		return ResponseEntity.ok(imageData);
	} else {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
}
