package com.popjub.userservice.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.popjub.userservice.domain.entity.LikeStore;

public interface LikeStoreRepository {

	Optional<LikeStore> findByUserIdAndStoreIdAndDeletedAtIsNull(Long userId, UUID storeId);

	LikeStore save(LikeStore likeStore);

	Page<LikeStore> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);

	Optional<LikeStore> findByLikeStoreIdAndUserIdAndDeletedAtIsNull(UUID likeStoreId, Long userId);
}
