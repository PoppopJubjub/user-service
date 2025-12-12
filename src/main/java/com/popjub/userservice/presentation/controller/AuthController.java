package com.popjub.userservice.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.response.ApiResponse;
import com.popjub.userservice.application.service.AuthService;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.presentation.dto.request.LoginUserRequest;
import com.popjub.userservice.presentation.dto.request.SignUpAdminRequest;
import com.popjub.userservice.presentation.dto.request.SignUpStoreManagerRequest;
import com.popjub.userservice.presentation.dto.request.SignUpUserRequest;
import com.popjub.userservice.presentation.dto.response.SignUpUserResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

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

	@PostMapping("/sign-up/admin")
	public ApiResponse<SignUpUserResponse> signUpAdmin(
		@Valid @RequestBody SignUpAdminRequest request
	) {
		User user = authService.signUpAdmin(request.toCommand());
		SignUpUserResponse response = SignUpUserResponse.from(user);

		return ApiResponse.of("어드민으로 회원가입이 완료되었습니다.", response);
	}

	@PostMapping("/login")
	public ApiResponse<Void> login(
		@RequestBody LoginUserRequest request,
		HttpServletResponse response
	) {
		String accessToken = authService.login(request.toCommand());
		response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);

		return ApiResponse.<Void>builder()
			.message("로그인에 성공했습니다.")
			.build();
	}
}