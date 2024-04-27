package com.fersko.storage.security;

import com.fersko.storage.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

	private static final Logger log = LoggerFactory.getLogger(SecurityService.class);
	@Value("${jwt.token.secret}")
	private String secret;
	@Value("${jwt.token.expiration}")
	private Integer expirationSec;


	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean isValid(String token, UserDetails userDetails) {
		return (extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignedKey())
				.build()
				.parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		Date expiration = extractClaims(token).getExpiration();
		return expiration.before(new Date());
	}

	public TokenDetails generateToken(UserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		if (user instanceof User customUserDetails) {
			claims.put("id", customUserDetails.getId());
			claims.put("role", customUserDetails.getRole());
		}
		return generateToken(claims, user.getUsername());
	}

	private String getSignedKey() {
		return Base64.getEncoder().encodeToString(secret.getBytes());
	}


	private TokenDetails generateToken(Map<String, Object> claims, String subject) {
		Date now = new Date(System.currentTimeMillis());
		Date expiration = new Date(now.getTime() + expirationSec);

		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS256, getSignedKey())
				.compact();

		log.info(String.valueOf(token.split("./").length == 3));

		return TokenDetails.builder()
				.id((Long) (claims.get("id")))
				.token(token)
				.createdAt(now)
				.issuedAt(expiration)
				.build();
	}

}
