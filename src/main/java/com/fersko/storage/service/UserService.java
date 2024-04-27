package com.fersko.storage.service;


import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

	UserDetails findByUsernameUserDetails(String username);
	User findByUsername(String username);
	List<ImageInfoDto> extractInfo(String username,Pageable pageable);

	User save(User user);

	UserDetailsService userDetailsService();
}
