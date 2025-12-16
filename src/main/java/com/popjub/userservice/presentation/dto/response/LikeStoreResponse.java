package com.popjub.userservice.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.userservice.domain.entity.LikeStore;

import lombok.Builder;

@Builder
public record LikeStoreResponse(
	UUID likeStoreId,
	Long userId,
	UUID storeId,
	LocalDateTime createdAt
) {
	public static LikeStoreResponse from(LikeStore likeStore) {
		return LikeStoreResponse.builder()
			.likeStoreId(likeStore.getLikeStoreId())
			.userId(likeStore.getUserId())
			.storeId(likeStore.getStoreId())
			.createdAt(likeStore.getCreatedAt())
			.build();
	}
}
