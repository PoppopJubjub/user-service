package com.popjub.userservice.presentation.dto.request;

import com.popjub.userservice.application.dto.command.UpdateUserCommand;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
	@Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
	String nickName,

	@Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다.")
	String userName,
	
	@Pattern(
		regexp = "^010-\\d{4}-\\d{4}$",
		message = "전화번호 형식이 올바르지 않습니다."
	)
	String phone
) {
	public UpdateUserCommand toCommand(Long userId) {
		return UpdateUserCommand.builder()
			.userId(userId)
			.nickName(nickName)
			.userName(userName)
			.phone(phone)
			.build();
	}
}
