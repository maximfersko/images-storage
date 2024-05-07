package com.fersko.storage.service;


import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

	UserDetails findByUsernameUserDetails(String username);

	boolean existsUserByUsername(String username);

	boolean existsUserByEmail(String email);

	User findByUsername(String username);

	Page<ImageInfoDto> extractInfo(String username, Pageable pageable);

	Page<ImageInfoDto> findAllImages(Pageable pageable);

	User save(User user);

	UserDetailsService userDetailsService();
}
