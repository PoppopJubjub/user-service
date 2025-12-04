package com.popjub.userservice.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.popjub.userservice.config.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(
			jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
		);
	}

	public String generateAccessToken(Long userId, String email, List<String> roles) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

		return Jwts.builder()
			.subject(userId.toString())
			.claim("email", email)
			.claim("roles", roles)
			.issuedAt(now)
			.expiration(expiryDate)
			.signWith(getSigningKey())
			.compact();
	}
}
