package com.popjub.userservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.popjub.userservice.domain.entity.LikeStore;
import com.popjub.userservice.domain.repository.LikeStoreRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LikeStoreRepositoryImpl implements LikeStoreRepository {

	private final LikeStoreJpaRepository likeStoreJpaRepository;

	@Override
	public Optional<LikeStore> findByUserIdAndStoreIdAndDeletedAtIsNull(Long userId, UUID storeId) {
		return likeStoreJpaRepository.findByUserIdAndStoreIdAndDeletedAtIsNull(userId, storeId);
	}

	@Override
	public LikeStore save(LikeStore likeStore) {
		return likeStoreJpaRepository.save(likeStore);
	}

	@Override
	public Page<LikeStore> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable) {
		return likeStoreJpaRepository.findByUserIdAndDeletedAtIsNull(userId, pageable);
	}
}
