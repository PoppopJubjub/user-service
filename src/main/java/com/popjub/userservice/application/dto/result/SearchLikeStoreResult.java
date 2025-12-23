package com.popjub.userservice.application.dto.result;

import java.util.UUID;

import com.popjub.userservice.domain.entity.LikeStore;

import lombok.Builder;

@Builder
public record SearchLikeStoreResult(
	UUID likeStoreId,
	UUID storeId
) {
	public static SearchLikeStoreResult from(LikeStore likeStore) {
		return SearchLikeStoreResult.builder()
			.likeStoreId(likeStore.getLikeStoreId())
			.storeId(likeStore.getStoreId())
			.build();
	}
}
