package com.fersko.storage.service;

import com.fersko.storage.dto.TokenDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {
	String extractUsername(String token);

	boolean isValid(String token, UserDetails userDetails);

	TokenDetails generateToken(UserDetails user);
}
