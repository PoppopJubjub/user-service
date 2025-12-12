package com.popjub.userservice.application.dto.command;

import com.popjub.userservice.domain.entity.User;

import lombok.Builder;

@Builder
public record SignUpAdminCommand(
	String email,
	String password,
	String nickName,
	String userName,
	String phone
) {
	public User toEntity(String encodedPassword) {
		return User.createAdmin(
			email,
			encodedPassword,
			nickName,
			userName,
			phone
		);
	}
}