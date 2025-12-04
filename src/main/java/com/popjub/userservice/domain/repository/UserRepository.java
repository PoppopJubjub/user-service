package com.popjub.userservice.domain.repository;

import java.util.Optional;

import com.popjub.userservice.domain.entity.User;

public interface UserRepository {

	User save(User user);

	boolean existsByEmail(String email);

	boolean existsByNickName(String nickName);

	Optional<User> findByEmail(String email);
}
