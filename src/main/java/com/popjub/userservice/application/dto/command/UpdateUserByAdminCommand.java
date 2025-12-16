package com.popjub.userservice.application.dto.command;

import com.popjub.userservice.domain.entity.UserRole;

import lombok.Builder;

@Builder
public record UpdateUserByAdminCommand(
	Long userId,
	String nickName,
	String userName,
	String phone,
	UserRole role
) {
}
