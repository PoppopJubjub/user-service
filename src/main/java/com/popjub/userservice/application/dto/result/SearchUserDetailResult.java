package com.popjub.userservice.application.dto.result;

import com.popjub.userservice.domain.entity.User;

public record SearchUserDetailResult(
	Long userId,
	String email,
	String nickname,
	String userName,
	String phone,
	String role,
	String slackUrl,
	String discordUrl
) {
	public static SearchUserDetailResult from(User user) {
		return new SearchUserDetailResult(
			user.getUserId(),
			user.getEmail(),
			user.getNickName(),
			user.getUserName(),
			user.getPhone(),
			user.getRole().name(),
			user.getSlackUrl(),
			user.getDiscordUrl()
		);
	}
}
