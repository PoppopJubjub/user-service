package com.popjub.userservice.presentation.dto.request;

import com.popjub.userservice.application.dto.command.ChangePasswordCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
	@NotBlank(message = "현재 비밀번호는 필수입니다.")
	String currentPassword,

	@NotBlank(message = "새 비밀번호는 필수입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
		message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
	)
	String newPassword,

	@NotBlank(message = "비밀번호 확인은 필수입니다.")
	String confirmPassword
) {
	public ChangePasswordCommand toCommand(Long userId) {
		return ChangePasswordCommand.builder()
			.userId(userId)
			.currentPassword(currentPassword)
			.newPassword(newPassword)
			.confirmPassword(confirmPassword)
			.build();
	}
}
