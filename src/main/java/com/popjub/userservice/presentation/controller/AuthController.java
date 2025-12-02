package com.popjub.userservice.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.response.ApiResponse;
import com.popjub.userservice.application.service.AuthService;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.presentation.dto.request.SignUpStoreManagerRequest;
import com.popjub.userservice.presentation.dto.request.SignUpUserRequest;
import com.popjub.userservice.presentation.dto.response.SignUpUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	/**
	 * 일반 회원가입용
	 */
	@PostMapping("/sign-up")
	public ApiResponse<SignUpUserResponse> signUpUser(
		@Valid @RequestBody SignUpUserRequest request
	) {
		User user = authService.signUpUser(request.toCommand());
		SignUpUserResponse response = SignUpUserResponse.from(user);

		return ApiResponse.of("회원가입이 완료되었습니다.", response);
	}

	/**
	 * 팝업매니저 회원가입용
	 */
	@PostMapping("/sign-up/manager")
	public ApiResponse<SignUpUserResponse> signUpStoreManager(
		@Valid @RequestBody SignUpStoreManagerRequest request
	) {
		User user = authService.signUpStoreManager(request.toCommand());
		SignUpUserResponse response = SignUpUserResponse.from(user);

		return ApiResponse.of("팝업관리자로 회원가입이 완료되었습니다.", response);
	}
}