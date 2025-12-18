package com.popjub.userservice.application.dto.command;

import lombok.Builder;

@Builder
public record UpdateUserCommand(
	Long userId,
	String nickName,
	String userName,
	String phone
) {
}
