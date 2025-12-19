package com.popjub.userservice.presentation.dto.response;

import java.util.UUID;

import com.popjub.userservice.application.dto.result.SearchLikeStoreResult;

import lombok.Builder;

@Builder
public record SearchLikeStoreResponse(
	UUID likeStoreId,
	UUID storeId
) {
	public static SearchLikeStoreResponse fromResult(SearchLikeStoreResult result) {
		return SearchLikeStoreResponse.builder()
			.likeStoreId(result.likeStoreId())
			.storeId(result.storeId())
			.build();
	}
}
