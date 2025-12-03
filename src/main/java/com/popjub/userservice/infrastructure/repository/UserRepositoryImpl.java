package com.popjub.userservice.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User save(User user) {
		return userJpaRepository.save(user);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userJpaRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByNickName(String nickName) {
		return userJpaRepository.existsByNickName(nickName);
	}
}
