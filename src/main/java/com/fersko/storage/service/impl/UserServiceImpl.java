package com.fersko.storage.service.impl;

import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.User;
import com.fersko.storage.mapper.ImageMapper;
import com.fersko.storage.repository.UserRepository;
import com.fersko.storage.security.UserDetailsFactory;
import com.fersko.storage.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final ImageMapper imageMapper;


	@Override
	public UserDetails findByUsernameUserDetails(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return UserDetailsFactory.create(user);
	}

	@Override
	public boolean existsUserByUsername(String username) {
		return userRepository.existsUserByUsername(username);
	}

	@Override
	public boolean existsUserByEmail(String email) {
		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImageInfoDto> extractInfo(String username, Pageable pageable) {
		List<Image> images = userRepository.
				findAllImagesByUserOrderByUploadedTimeDesc(username, pageable);
		return getImageInfoDtos(images);
	}

	private List<ImageInfoDto> getImageInfoDtos(List<Image> images) {
		return images.stream()
				.map(imageMapper::imageToImageInfoDto)
				.toList();
	}

	@Override
	public List<ImageInfoDto> findAllImages(Pageable pageable) {
		List<Image> allImages = userRepository.findAllImages(pageable);
		return getImageInfoDtos(allImages);
	}


	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public UserDetailsService userDetailsService() {
		return this::findByUsernameUserDetails;
	}


}
