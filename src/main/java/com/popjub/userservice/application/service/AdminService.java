package com.popjub.userservice.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.userservice.application.dto.command.CreateUserCommand;
import com.popjub.userservice.application.dto.result.SearchUserDetailResult;
import com.popjub.userservice.application.dto.result.SearchUserResult;
import com.popjub.userservice.domain.entity.User;
import com.popjub.userservice.domain.entity.UserRole;
import com.popjub.userservice.domain.repository.UserRepository;
import com.popjub.userservice.exception.UserCustomException;
import com.popjub.userservice.exception.UserErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User createUser(CreateUserCommand command) {

		if (command.role() == UserRole.ADMIN) {
			throw new UserCustomException(UserErrorCode.CANNOT_CREATE_ADMIN);
		}

		if (userRepository.existsByEmail(command.email())) {
			throw new UserCustomException(UserErrorCode.DUPLICATE_EMAIL);
		}

		if (userRepository.existsByNickName(command.nickName())) {
			throw new UserCustomException(UserErrorCode.DUPLICATE_NICKNAME);
		}

		String encodedPassword = passwordEncoder.encode(command.password());

		User user = command.toEntity(encodedPassword);

		User savedUser = userRepository.save(user);

		log.info("관리자가 유저 생성 - userId: {}, email: {}, role: {}",
			savedUser.getUserId(),
			savedUser.getEmail(),
			savedUser.getRole()
		);

		return savedUser;
	}

	public SearchUserDetailResult getUserDetailByAdmin(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserCustomException(UserErrorCode.NOT_FOUND_USER));

		return SearchUserDetailResult.from(user);
	}

	public Page<SearchUserResult> getAllUsers(Pageable pageable) {
		Page<User> userPage = userRepository.findAll(pageable);

		return userPage.map(SearchUserResult::from);
	}
}
