package com.popjub.userservice.application.dto.command;

import com.popjub.userservice.domain.entity.User;

import lombok.Builder;

@Builder
public record SignUpUserCommand(
	String email,
	String password,
	String nickName,
	String userName,
	String phone
) {
	public User toEntity(String encodedPassword) {
		return User.createUser(
			email,
			encodedPassword,
			nickName,
			userName,
			phone
		);
	}
}
