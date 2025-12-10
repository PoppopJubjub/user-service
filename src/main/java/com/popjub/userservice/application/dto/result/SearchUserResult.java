package com.popjub.userservice.application.dto.result;

import java.time.LocalDateTime;

import com.popjub.userservice.domain.entity.User;

import lombok.Builder;

@Builder
public record SearchUserResult(
	Long userId,
	String email,
	String nickname,
	String userName,
	LocalDateTime createdAt
) {
	public static SearchUserResult from(User user) {
		return SearchUserResult.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.nickname(user.getNickName())
			.userName(user.getUserName())
			.createdAt(user.getCreatedAt())
			.build();
	}
}
