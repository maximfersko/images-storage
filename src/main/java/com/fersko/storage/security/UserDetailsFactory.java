package com.fersko.storage.security;

import com.fersko.storage.entity.User;

public class UserDetailsFactory {
	private UserDetailsFactory() {

	}

	public static JwtUserDetails create(User user) {
		return JwtUserDetails.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.role(user.getRole())
				.id(user.getId())
				.build();
	}
}
