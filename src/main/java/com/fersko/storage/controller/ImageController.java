package com.fersko.storage.controller;

import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.User;
import com.fersko.storage.exceptions.FileUploadException;
import com.fersko.storage.exceptions.ImageNotFoundException;
import com.fersko.storage.service.ImageService;
import com.fersko.storage.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/storage")
public class ImageController {
	private final int PAGE_SIZE = 10;
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
				throw new FileUploadException("File upload failed");
			}
		}
		return "redirect:/storage";
	}

	@GetMapping("/image")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllImagesByUser(@RequestParam("page") int page) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Page<ImageInfoDto> imagePage = userService.extractInfo(username, PageRequest.of(page, PAGE_SIZE));

		return getMapResponse(imagePage);
	}

	@GetMapping("/admin/image")
//	@PreAuthorize("")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAllImagesByAdmin(@RequestParam("page") int page) {
		Page<ImageInfoDto> images = userService.findAllImages(PageRequest.of(page, PAGE_SIZE));
		return getMapResponse(images);
	}

	private ResponseEntity<Map<String, Object>> getMapResponse(Page<ImageInfoDto> images) {
		return ResponseEntity.ok(
				new HashMap<>() {{
					put("images", images.getContent());
					put("currentPage", images.getNumber());
					put("totalItems", images.getTotalElements());
					put("totalPages", images.getTotalPages());
				}}
		);
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

	@DeleteMapping("/image/{id}")
	@ResponseBody
	public ResponseEntity<String> deleteImageById(@PathVariable("id") Long id) {
		try {
			imageService.deleteById(id);
		} catch (ImageNotFoundException e) {
			log.info("Image with id {} not found", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
