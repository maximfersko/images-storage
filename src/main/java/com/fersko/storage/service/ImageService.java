package com.fersko.storage.service;

import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
	Image save(MultipartFile image, User user) throws IOException;



	Image findImageById(Long id);
}
