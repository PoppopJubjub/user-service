package com.popjub.userservice.exception;

import org.springframework.http.HttpStatus;

import com.popjub.common.exception.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
	/**
	 * 인증
	 */
	INVALID_CREDENTIALS("이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
	/**
	 * 회원
	 */
	DUPLICATE_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
	DUPLICATE_NICKNAME("이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
	CANNOT_CREATE_ADMIN("ADMIN 역할은 생성할 수 없습니다.", HttpStatus.BAD_REQUEST),
	NOT_FOUND_USER("존재하지 않은 사용자입니다.", HttpStatus.NOT_FOUND),
	/**
	 * 관심 팝업
	 */
	NOT_FOUND_STORE("존재하지 않는 팝업입니다.", HttpStatus.NOT_FOUND),
	ALREADY_LIKED_STORE("이미 관심 등록된 팝업입니다.", HttpStatus.CONFLICT),
	/**
	 * Validation
	 */
	INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus status;
}