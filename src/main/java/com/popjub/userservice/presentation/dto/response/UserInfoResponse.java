package com.popjub.userservice.presentation.dto.response;

import com.popjub.userservice.application.dto.result.UserInfoResult;

import lombok.Builder;

@Builder
public record UserInfoResponse(
	Long userId,
	String userName,
	String discordUrl,
	String slackUrl
) {
	public static UserInfoResponse fromResult(UserInfoResult result) {
		return UserInfoResponse.builder()
			.userId(result.userId())
			.userName(result.userName())
			.discordUrl(result.discordUrl())
			.slackUrl(result.slackUrl())
			.build();
	}
}
