package com.popjub.userservice.application.dto.result;

import com.popjub.userservice.domain.entity.User;

import lombok.Builder;

@Builder
public record UserInfoResult(
	Long userId,
	String discordUrl,
	String slackUrl
) {
	public static UserInfoResult from(User user) {
		return UserInfoResult.builder()
			.userId(user.getUserId())
			.discordUrl(user.getDiscordUrl())
			.slackUrl(user.getSlackUrl())
			.build();
	}
}
