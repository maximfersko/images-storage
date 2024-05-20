package com.fersko.storage.repository;

import com.fersko.storage.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	@Query("""
			SELECT t FROM Token t
			JOIN User  u ON t.user.id = u.id
			where t.user.id = :userId AND t.loggedOut = false
			""")
	List<Token> findAllByTokenByUserId(Long userId);

	Optional<Token> findByToken(String token);

}
