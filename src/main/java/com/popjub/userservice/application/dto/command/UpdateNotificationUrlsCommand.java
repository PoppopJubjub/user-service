package com.popjub.userservice.application.dto.command;

import lombok.Builder;

@Builder
public record UpdateNotificationUrlsCommand(
	Long userId,
	String slackUrl,
	String discordUrl
) {
}
