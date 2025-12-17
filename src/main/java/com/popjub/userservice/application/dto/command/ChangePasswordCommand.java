package com.popjub.userservice.application.dto.command;

import lombok.Builder;

@Builder
public record ChangePasswordCommand(
	Long userId,
	String currentPassword,
	String newPassword,
	String confirmPassword
) {
}
