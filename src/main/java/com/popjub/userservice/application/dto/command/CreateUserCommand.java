package com.popjub.userservice.application.dto.command;

import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.domain.entity.UserRole;

import lombok.Builder;

@Builder
public record CreateUserCommand(
	String email,
	String password,
	String nickName,
	String userName,
	String phone,
	UserRole role
) {
	public User toEntity(String encodedPassword) {
		return User.createUserWithRole(
			email,
			encodedPassword,
			nickName,
			userName,
			phone,
			role
		);
	}
}
