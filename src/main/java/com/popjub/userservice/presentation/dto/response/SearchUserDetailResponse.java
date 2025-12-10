package com.popjub.userservice.presentation.dto.response;

import com.popjub.userservice.application.dto.result.SearchUserDetailResult;

public record SearchUserDetailResponse(
	Long userId,
	String email,
	String nickname,
	String userName,
	String phone,
	String role,
	String slackUrl,
	String discordUrl
) {
	public static SearchUserDetailResponse from(SearchUserDetailResult result) {
		return new SearchUserDetailResponse(
			result.userId(),
			result.email(),
			result.nickname(),
			result.userName(),
			result.phone(),
			result.role(),
			result.slackUrl(),
			result.discordUrl()
		);
	}
}

