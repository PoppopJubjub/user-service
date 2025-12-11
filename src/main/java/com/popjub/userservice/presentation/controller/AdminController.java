package com.popjub.userservice.presentation.controller;

import static com.popjub.common.enums.UserRole.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.annotation.RoleCheck;
import com.popjub.common.response.ApiResponse;
import com.popjub.common.response.PageResponse;
import com.popjub.userservice.application.dto.result.SearchUserDetailResult;
import com.popjub.userservice.application.dto.result.SearchUserResult;
import com.popjub.userservice.application.service.AdminService;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.presentation.dto.request.CreateUserRequest;
import com.popjub.userservice.presentation.dto.response.SearchUserDetailResponse;
import com.popjub.userservice.presentation.dto.response.SearchUserResponse;
import com.popjub.userservice.presentation.dto.response.SignUpUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

	private final AdminService adminService;

	@RoleCheck(ADMIN)
	@PostMapping("/users")
	public ApiResponse<SignUpUserResponse> createUser(
		@Valid @RequestBody CreateUserRequest request
	) {
		User user = adminService.createUser(request.toCommand());
		SignUpUserResponse response = SignUpUserResponse.from(user);

		return ApiResponse.of("회원이 생성되었습니다.", response);
	}

	@RoleCheck(ADMIN)
	@GetMapping("/users/{userId}")
	public ApiResponse<SearchUserDetailResponse> getUserDetailByAdmin(
		@PathVariable Long userId
	) {
		SearchUserDetailResult result = adminService.getUserDetailByAdmin(userId);
		SearchUserDetailResponse response = SearchUserDetailResponse.fromResult(result);

		return ApiResponse.of("특정사용자의 정보 조회에 성공했습니다.", response);
	}

	@RoleCheck(ADMIN)
	@GetMapping("/users")
	public ApiResponse<PageResponse<SearchUserResponse>> getAllUsers(
		@PageableDefault(
			size = 10,
			sort = "createdAt",
			direction = Sort.Direction.DESC
		) Pageable pageable
	) {
		Page<SearchUserResult> resultPage = adminService.getAllUsers(pageable);
		Page<SearchUserResponse> responsePage = resultPage.map(SearchUserResponse::fromResult);
		PageResponse<SearchUserResponse> pageResponse = PageResponse.from(responsePage);

		return ApiResponse.of("유저 전체조회에 성공했습니다", pageResponse);
	}
}
