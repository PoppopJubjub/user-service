package com.popjub.userservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.userservice.domain.entity.LikeStore;

public interface LikeStoreJpaRepository extends JpaRepository<LikeStore, UUID> {

	Optional<LikeStore> findByUserIdAndStoreIdAndDeletedAtIsNull(Long userId, UUID storeId);
}
