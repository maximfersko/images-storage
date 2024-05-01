package com.fersko.storage.repository;

import com.fersko.storage.entity.Image;
import com.fersko.storage.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsUserByUsername(String username);

	boolean existsUserByEmail(String email);

	@Query("""
			SELECT i FROM Image i 
			WHERE i.user.username = :username 
			ORDER BY i.uploadedTime DESC
			""")
	List<Image> findAllImagesByUserOrderByUploadedTimeDesc(@Param("username") String username, Pageable pageable);
}
