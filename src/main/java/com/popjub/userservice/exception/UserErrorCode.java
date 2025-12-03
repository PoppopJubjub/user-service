package com.popjub.userservice.exception;

import org.springframework.http.HttpStatus;

import com.popjub.common.exception.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
	/**
	 * 회원
	 */
	DUPLICATE_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
	DUPLICATE_NICKNAME("이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
	CANNOT_CREATE_ADMIN("ADMIN 역할은 생성할 수 없습니다.", HttpStatus.BAD_REQUEST),
	/**
	 * Validation
	 */
	INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus status;
}