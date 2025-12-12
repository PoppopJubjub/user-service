package com.popjub.userservice.presentation.controller.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.userservice.application.dto.result.UserInfoResult;
import com.popjub.userservice.application.service.UserService;
import com.popjub.userservice.presentation.dto.response.UserInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/users")
public class UserInternalController {

	private final UserService userService;

	@GetMapping("/{userId}")
	public UserInfoResponse getUserInfo(
		@PathVariable Long userId
	) {
		log.info("[내부 API] 사용자 Webhook URL 조회 - userId: {}", userId);
		UserInfoResult result = userService.getUserWebhookUrls(userId);

		return UserInfoResponse.fromResult(result);
	}
}
