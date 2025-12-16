package com.popjub.userservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.userservice.application.dto.command.CreateLikeStoreCommand;

import jakarta.validation.constraints.NotNull;

public record CreateLikeStoreRequest(
	@NotNull(message = "매장 ID는 필수입니다.")
	UUID storeId
) {

	public CreateLikeStoreCommand toCommand(Long userId) {
		return CreateLikeStoreCommand.builder()
			.userId(userId)
			.storeId(storeId)
			.build();
	}
}
