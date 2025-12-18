package com.popjub.userservice.presentation.controller;

import static com.popjub.common.enums.UserRole.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.annotation.CurrentUser;
import com.popjub.common.annotation.RoleCheck;
import com.popjub.common.enums.SuccessCode;
import com.popjub.common.response.ApiResponse;
import com.popjub.userservice.application.dto.result.SearchUserDetailResult;
import com.popjub.userservice.application.service.UserService;
import com.popjub.userservice.domain.entity.LikeStore;
import com.popjub.userservice.presentation.dto.request.ChangePasswordRequest;
import com.popjub.userservice.presentation.dto.request.CreateLikeStoreRequest;
import com.popjub.userservice.presentation.dto.request.UpdateNotificationUrlsRequest;
import com.popjub.userservice.presentation.dto.request.UpdateUserRequest;
import com.popjub.userservice.presentation.dto.response.LikeStoreResponse;
import com.popjub.userservice.presentation.dto.response.SearchUserDetailResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PutMapping("/me/notification-urls")
	@RoleCheck({USER, STORE_MANAGER, ADMIN})
	public ApiResponse<Void> updateUserNotificationUrls(
		@RequestBody UpdateNotificationUrlsRequest request,
		@CurrentUser Long userId
	) {
		userService.updateNotificationUrls(request.toCommand(userId));

		return ApiResponse.<Void>builder()
			.message("알림받을 디스코드/슬랙 url 등록에 성공했습니다.")
			.code(SuccessCode.OK)
			.build();
	}

	@PutMapping("/me")
	public ApiResponse<Void> updateMyProfile(
		@Valid @RequestBody UpdateUserRequest request,
		@CurrentUser Long userId
	) {
		userService.updateMyProfile(request.toCommand(userId));

		return ApiResponse.<Void>builder()
			.message("내 정보 수정에 성공했습니다.")
			.code(SuccessCode.OK)
			.build();
	}

	@GetMapping("/me")
	@RoleCheck({USER, STORE_MANAGER, ADMIN})
	public ApiResponse<SearchUserDetailResponse> getMyProfile(@CurrentUser Long userId) {
		SearchUserDetailResult result = userService.getMyProfile(userId);
		SearchUserDetailResponse response = SearchUserDetailResponse.fromResult(result);

		return ApiResponse.of("내 정보 조회에 성공했습니다.", response);
	}

	@PostMapping("/me/like-stores")
	public ApiResponse<LikeStoreResponse> createLikeStore(
		@Valid @RequestBody CreateLikeStoreRequest request,
		@CurrentUser Long userId
	) {
		LikeStore likeStore = userService.createLikeStore(request.toCommand(userId));
		LikeStoreResponse response = LikeStoreResponse.from(likeStore);

		return ApiResponse.of("관심 팝업 등록에 성공했습니다.", response);
	}

	@PutMapping("/me/password")
	public ApiResponse<Void> changePassword(
		@Valid @RequestBody ChangePasswordRequest request,
		@CurrentUser Long userId
	) {
		userService.changePassword(request.toCommand(userId));

		return ApiResponse.<Void>builder()
			.message("비밀번호 변경에 성공했습니다.")
			.code(SuccessCode.OK)
			.build();
	}
}
