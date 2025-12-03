package com.popjub.userservice.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.userservice.domain.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	boolean existsByNickName(String nickName);

	Optional<User> findByEmail(String email);
}
