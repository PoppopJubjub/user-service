package com.popjub.userservice.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.annotation.CurrentUser;
import com.popjub.common.annotation.RoleCheck;
import com.popjub.common.enums.SuccessCode;
import com.popjub.common.enums.UserRole;
import com.popjub.common.response.ApiResponse;
import com.popjub.userservice.application.dto.result.SearchUserDetailResult;
import com.popjub.userservice.application.service.UserService;
import com.popjub.userservice.presentation.dto.request.UpdateNotificationUrlsRequest;
import com.popjub.userservice.presentation.dto.response.SearchUserDetailResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PutMapping("/me/notification-urls")
	public ApiResponse<Void> updateUserNotificationUrls(
		@RequestBody UpdateNotificationUrlsRequest request
	) {
		/**
		 * TODO: @CurrentUser 로 바꿀 예정 - 임시 userId = 1
		 */
		Long userId = 1L;

		userService.updateNotificationUrls(request.toCommand(userId));

		return ApiResponse.<Void>builder()
			.message("알림받을 디스코드/슬랙 url 등록에 성공했습니다.")
			.code(SuccessCode.OK)
			.build();
	}

	@GetMapping("/me")
	@RoleCheck({UserRole.USER, UserRole.STORE_MANAGER, UserRole.ADMIN})
	public ApiResponse<SearchUserDetailResponse> getMyProfile(@CurrentUser Long userId) {
		SearchUserDetailResult result = userService.getMyProfile(userId);
		SearchUserDetailResponse response = SearchUserDetailResponse.from(result);
		return ApiResponse.of("내 정보 조회에 성공했습니다.", response);
	}
}
