package com.popjub.userservice.domain.repository;

import com.popjub.userservice.domain.entity.User;

public interface UserRepository {

	User save(User user);

	boolean existsByEmail(String email);

	boolean existsByNickName(String nickName);
}
