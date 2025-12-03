package com.popjub.userservice.presentation.dto.request;

import com.popjub.userservice.application.dto.command.LoginUserCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수입니다.")
	String password
) {
	public LoginUserCommand toCommand() {
		return LoginUserCommand.builder()
			.email(email)
			.password(password)
			.build();
	}
}
