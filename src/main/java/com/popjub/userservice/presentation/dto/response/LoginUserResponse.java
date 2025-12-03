package com.popjub.userservice.presentation.dto.response;

import lombok.Builder;

@Builder
public record LoginUserResponse(
	String accessToken,
	String tokenType
) {
	public static LoginUserResponse of(String accessToken) {
		return LoginUserResponse.builder()
			.accessToken(accessToken)
			.tokenType("Bearer")
			.build();
	}
}
