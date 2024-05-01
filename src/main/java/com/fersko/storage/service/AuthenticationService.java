package com.fersko.storage.service;

import com.fersko.storage.dto.SignInRequest;
import com.fersko.storage.dto.SignUpRequest;
import com.fersko.storage.dto.TokenDetails;
import com.fersko.storage.entity.User;

public interface AuthenticationService {
	TokenDetails signIn(SignInRequest request);

	User signUp(SignUpRequest request);
}
