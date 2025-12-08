package com.popjub.userservice.application.service;

import static com.popjub.userservice.exception.UserErrorCode.*;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.userservice.application.dto.command.LoginUserCommand;
import com.popjub.userservice.application.dto.command.SignUpStoreManagerCommand;
import com.popjub.userservice.application.dto.command.SignUpUserCommand;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.domain.repository.UserRepository;
import com.popjub.userservice.exception.UserCustomException;
import com.popjub.userservice.infrastructure.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public User signUpUser(SignUpUserCommand command) {
		validateEmailDuplication(command.email());
		validateNickNameDuplication(command.nickName());

		String encodedPassword = passwordEncoder.encode(command.password());

		User user = command.toEntity(encodedPassword);

		User savedUser = userRepository.save(user);

		log.info("회원가입 완료(USER) - userId: {}, userName: {}, role: {}",
			savedUser.getUserId(),
			savedUser.getUserName(),
			savedUser.getRole()
		);
		return savedUser;
	}

	@Transactional
	public User signUpStoreManager(SignUpStoreManagerCommand command) {
		validateEmailDuplication(command.email());
		validateNickNameDuplication(command.nickName());

		String encodedPassword = passwordEncoder.encode(command.password());

		User user = command.toEntity(encodedPassword);

		User savedUser = userRepository.save(user);

		log.info("회원가입 완료(STORE_MANAGER) - userId: {}, userName: {}, role: {}",
			savedUser.getUserId(),
			savedUser.getUserName(),
			savedUser.getRole()
		);
		return savedUser;
	}

	public String login(LoginUserCommand command) {
		User user = userRepository.findByEmail(command.email()).orElseThrow(
			() -> new UserCustomException(INVALID_CREDENTIALS)
		);

		if (!passwordEncoder.matches(command.password(), user.getPassword())) {
			throw new UserCustomException(INVALID_CREDENTIALS);
		}

		List<String> roles = List.of(user.getRole().name());
		String accessToken = jwtTokenProvider.generateAccessToken(
			user.getUserId(),
			user.getUserName(),
			roles
		);

		log.info("로그인 성공 - userId: {}, userName: {}", user.getUserId(), user.getUserName());

		return accessToken;
	}

	private void validateEmailDuplication(String email) {
		if (userRepository.existsByEmail(email)) {
			log.warn("이메일 중복 - email: {}", email);
			throw new UserCustomException(DUPLICATE_EMAIL);
		}
	}

	private void validateNickNameDuplication(String nickName) {
		if (userRepository.existsByNickName(nickName)) {
			log.warn("닉네임 중복 - nickName: {}", nickName);
			throw new UserCustomException(DUPLICATE_NICKNAME);
		}
	}
}
