package com.fersko.storage.service;

import com.fersko.storage.entity.Token;

import java.util.List;
import java.util.Optional;


public interface TokenService {
	Token save(Token token);

	Optional<Token> findByToken(String token);

	List<Token> findAllByTokenByUserId(Long userId);

	List<Token> saveAll(List<Token> tokens);
}
