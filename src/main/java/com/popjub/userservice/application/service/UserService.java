package com.popjub.userservice.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.userservice.application.dto.command.ChangePasswordCommand;
import com.popjub.userservice.application.dto.command.CreateLikeStoreCommand;
import com.popjub.userservice.application.dto.command.UpdateNotificationUrlsCommand;
import com.popjub.userservice.application.dto.command.UpdateUserCommand;
import com.popjub.userservice.application.dto.result.SearchLikeStoreResult;
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
	private final PasswordEncoder passwordEncoder;

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

	@Transactional
	public void updateMyProfile(UpdateUserCommand command) {
		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		validateNickNameDuplicate(command.nickName(), user);

		user.updateMyProfile(command.nickName(), command.userName(), command.phone());

		log.info("내 정보 수정 성공 - userId: {}", command.userId());
	}

	public SearchUserDetailResult getMyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		log.info("내 정보 조회 성공 - userId: {}", userId);

		return SearchUserDetailResult.from(user);
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

	public UserInfoResult getUserWebhookUrls(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		return UserInfoResult.from(user);
	}

	@Transactional
	public void changePassword(ChangePasswordCommand command) {
		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		validateCurrentPassword(command.currentPassword(), user.getPassword());
		validatePasswordMatch(command.newPassword(), command.confirmPassword());
		validatePasswordNotSame(command.newPassword(), user.getPassword());

		String encodedPassword = passwordEncoder.encode(command.newPassword());
		user.changePassword(encodedPassword);

		log.info("비밀번호 변경 성공 - userId: {}", command.userId());
	}

	public Page<SearchLikeStoreResult> getLikeStores(Long userId, Pageable pageable) {
		Page<LikeStore> likeStores = likeStoreRepository.findByUserIdAndDeletedAtIsNull(userId, pageable);

		return likeStores.map(SearchLikeStoreResult::from);
	}
	
	/**  private Method 구분선  */
	private void validateNickNameDuplicate(String newNickName, User user) {
		if (user.isDifferentNickName(newNickName)) {
			if (userRepository.existsByNickName(newNickName)) {
				throw new UserCustomException(UserErrorCode.DUPLICATE_NICKNAME);
			}
		}
	}

	private boolean isStoreNotFound(CreateLikeStoreCommand command) {
		return !storeServicePort.exists(command.storeId());
	}

	private void validateCurrentPassword(String inputPassword, String storedPassword) {
		if (!passwordEncoder.matches(inputPassword, storedPassword)) {
			throw new UserCustomException(UserErrorCode.INVALID_CURRENT_PASSWORD);
		}
	}

	private void validatePasswordMatch(String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword)) {
			throw new UserCustomException(UserErrorCode.PASSWORD_NOT_MATCH);
		}
	}

	private void validatePasswordNotSame(String newPassword, String currentEncodedPassword) {
		if (passwordEncoder.matches(newPassword, currentEncodedPassword)) {
			throw new UserCustomException(UserErrorCode.SAME_AS_CURRENT_PASSWORD);
		}
	}
}
