package com.popjub.userservice.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.popjub.userservice.domain.entity.User;

public interface UserRepository {

	User save(User user);

	boolean existsByEmail(String email);

	boolean existsByNickName(String nickName);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long userId);

	Page<User> findAll(Pageable pageable);
}
