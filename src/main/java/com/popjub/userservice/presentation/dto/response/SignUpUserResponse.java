package com.popjub.userservice.presentation.dto.response;

import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.domain.entity.UserRole;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SignUpUserResponse(
	Long userId,
	String email,
	String nickName,
	String userName,
	String phone,
	UserRole role
) {
	public static SignUpUserResponse from(User user) {
		return SignUpUserResponse.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.nickName(user.getNickName())
			.userName(user.getUserName())
			.phone(user.getPhone())
			.role(user.getRole())
			.build();
	}
}
