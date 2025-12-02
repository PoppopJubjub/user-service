package com.popjub.userservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserCustomException extends RuntimeException {

	private final UserErrorCode errorCode;
	private final String message;
	private final HttpStatus status;

	public UserCustomException(UserErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
	}
}