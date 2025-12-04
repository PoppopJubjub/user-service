package com.popjub.userservice.application.dto.command;

import lombok.Builder;

@Builder
public record LoginUserCommand(
	String email,
	String password
) {
}
