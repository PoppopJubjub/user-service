package com.popjub.userservice.presentation.dto.response;

import java.time.LocalDateTime;

import com.popjub.userservice.application.dto.result.SearchUserResult;

import lombok.Builder;

@Builder
public record SearchUserResponse(
	Long userId,
	String email,
	String nickname,
	String userName,
	LocalDateTime createdAt
) {

	public static SearchUserResponse fromResult(SearchUserResult searchUserResult) {
		return SearchUserResponse.builder()
			.userId(searchUserResult.userId())
			.email(searchUserResult.email())
			.nickname(searchUserResult.nickname())
			.userName(searchUserResult.userName())
			.createdAt(searchUserResult.createdAt())
			.build();
	}
}
