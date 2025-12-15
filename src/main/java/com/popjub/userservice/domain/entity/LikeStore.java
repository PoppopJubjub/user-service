package com.popjub.userservice.domain.entity;

import java.util.UUID;

import com.popjub.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "p_like_store", schema = "users_schema")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeStore extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID likeStoreId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "store_id", nullable = false)
	private UUID storeId;

	@Builder(access = AccessLevel.PRIVATE)
	private LikeStore(Long userId, UUID storeId) {
		this.userId = userId;
		this.storeId = storeId;
	}

	public static LikeStore of(Long userId, UUID storeId) {
		return LikeStore.builder()
			.userId(userId)
			.storeId(storeId)
			.build();
	}
}
