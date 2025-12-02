package com.popjub.userservice.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.response.ApiResponse;
import com.popjub.userservice.application.service.AdminService;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.presentation.dto.request.CreateUserRequest;
import com.popjub.userservice.presentation.dto.response.SignUpUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

	private final AdminService adminService;

	/**
	 * TODO: RoleCheck ADMIN
	 */
	@PostMapping("/users")
	public ApiResponse<SignUpUserResponse> createUser(
		@Valid @RequestBody CreateUserRequest request
	) {
		User user = adminService.createUser(request.toCommand());
		SignUpUserResponse response = SignUpUserResponse.from(user);

		return ApiResponse.of("회원이 생성되었습니다.", response);
	}
}
