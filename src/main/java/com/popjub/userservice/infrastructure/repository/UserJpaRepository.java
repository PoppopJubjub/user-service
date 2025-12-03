package com.popjub.userservice.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.userservice.domain.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	boolean existsByNickName(String nickName);
}
