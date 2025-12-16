package com.popjub.userservice.application.dto.command;

import java.util.UUID;

import com.popjub.userservice.domain.entity.LikeStore;

import lombok.Builder;

@Builder
public record CreateLikeStoreCommand(
	Long userId,
	UUID storeId
) {
	public LikeStore toEntity() {
		return LikeStore.of(
			userId,
			storeId
		);
	}
}
