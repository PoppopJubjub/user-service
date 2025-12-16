package com.popjub.userservice.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.userservice.application.dto.command.CreateLikeStoreCommand;
import com.popjub.userservice.application.dto.command.UpdateNotificationUrlsCommand;
import com.popjub.userservice.application.dto.result.SearchUserDetailResult;
import com.popjub.userservice.application.dto.result.UserInfoResult;
import com.popjub.userservice.application.port.StoreServicePort;
import com.popjub.userservice.domain.entity.LikeStore;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.domain.repository.LikeStoreRepository;
import com.popjub.userservice.domain.repository.UserRepository;
import com.popjub.userservice.exception.UserCustomException;
import com.popjub.userservice.exception.UserErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final LikeStoreRepository likeStoreRepository;
	private final StoreServicePort storeServicePort;

	@Transactional
	public void updateNotificationUrls(UpdateNotificationUrlsCommand command) {
		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		user.updateNotificationUrls(command.slackUrl(), command.discordUrl());

		log.info("알림 URL 업데이트 성공 - userId: {}, slackUrl: {}, discordUrl: {}",
			command.userId(),
			command.slackUrl() != null ? "설정됨" : "미설정",
			command.discordUrl() != null ? "설정됨" : "미설정"
		);
	}

	public SearchUserDetailResult getMyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		log.info("내 정보 조회 성공 - userId: {}", userId);

		return SearchUserDetailResult.from(user);
	}

	public UserInfoResult getUserWebhookUrls(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		return UserInfoResult.from(user);
	}

	@Transactional
	public LikeStore createLikeStore(CreateLikeStoreCommand command) {

		if (isStoreNotFound(command)) {
			throw new UserCustomException(UserErrorCode.NOT_FOUND_STORE);
		}

		likeStoreRepository.findByUserIdAndStoreIdAndDeletedAtIsNull(
			command.userId(),
			command.storeId()
		).ifPresent(like -> {
				throw new UserCustomException(UserErrorCode.ALREADY_LIKED_STORE);
			}
		);

		LikeStore likeStore = command.toEntity();
		LikeStore saved = likeStoreRepository.save(likeStore);
		log.info("관심 팝업 등록 - userId: {}, storeId: {}", command.userId(), command.storeId());

		return saved;
	}

	private boolean isStoreNotFound(CreateLikeStoreCommand command) {
		return !storeServicePort.exists(command.storeId());
	}
}
