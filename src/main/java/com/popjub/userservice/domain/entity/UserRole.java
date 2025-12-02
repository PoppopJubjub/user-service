package com.popjub.userservice.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

	ADMIN("관리자"),
	STORE_MANAGER("팝업 관리자"),
	USER("일반 사용자");

	private final String description;
}
