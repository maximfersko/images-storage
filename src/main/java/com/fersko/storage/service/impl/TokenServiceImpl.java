package com.fersko.storage.service.impl;

import com.fersko.storage.entity.Token;
import com.fersko.storage.repository.TokenRepository;
import com.fersko.storage.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
	private final TokenRepository tokenRepository;

	@Override
	public Token save(Token token) {
		return tokenRepository.save(token);
	}

	@Override
	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public List<Token> findAllByTokenByUserId(Long userId) {
		return tokenRepository.findAllByTokenByUserId(userId);
	}

	@Override
	public List<Token> saveAll(List<Token> tokens) {
		return tokenRepository.saveAll(tokens);
	}
}
