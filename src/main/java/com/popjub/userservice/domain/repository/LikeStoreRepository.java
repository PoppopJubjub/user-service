package com.popjub.userservice.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.popjub.userservice.domain.entity.LikeStore;

public interface LikeStoreRepository {

	Optional<LikeStore> findByUserIdAndStoreIdAndDeletedAtIsNull(Long userId, UUID storeId);

	LikeStore save(LikeStore likeStore);
}
