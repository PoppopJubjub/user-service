package com.popjub.userservice.presentation.dto.request;

import com.popjub.userservice.application.dto.command.UpdateNotificationUrlsCommand;

public record UpdateNotificationUrlsRequest(
	String slackUrl,
	String discordUrl
) {
	public UpdateNotificationUrlsCommand toCommand(Long userId) {
		return UpdateNotificationUrlsCommand.builder()
			.userId(userId)
			.slackUrl(slackUrl)
			.discordUrl(discordUrl)
			.build();
	}
}
