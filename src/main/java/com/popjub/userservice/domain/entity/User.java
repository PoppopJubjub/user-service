package com.popjub.userservice.domain.entity;

import com.popjub.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_user", schema = "users_schema")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickName;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@Column(name = "slack_url")
	private String slackUrl;

	@Column(name = "discord_url")
	private String discordUrl;

	@Builder(access = AccessLevel.PRIVATE)
	private User(String email, String password, String nickName, String userName, String phone, UserRole role) {
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.userName = userName;
		this.phone = phone;
		this.role = role;
	}

	/**
	 * 일반 회원 생성(USER)
	 */
	public static User createUser(String email, String password, String nickName, String userName, String phone) {
		return User.builder()
			.email(email)
			.password(password)
			.nickName(nickName)
			.userName(userName)
			.phone(phone)
			.role(UserRole.USER)
			.build();
	}

	/**
	 * 팝업 매니저 생성(STORE_MANAGER)
	 */
	public static User createStoreManager(String email, String password, String nickName, String userName,
		String phone) {
		return User.builder()
			.email(email)
			.password(password)
			.nickName(nickName)
			.userName(userName)
			.phone(phone)
			.role(UserRole.STORE_MANAGER)
			.build();
	}

	/**
	 * 어드민 생성(ADMIN)
	 */
	public static User createAdmin(String email, String password, String nickName, String userName,
		String phone) {
		return User.builder()
			.email(email)
			.password(password)
			.nickName(nickName)
			.userName(userName)
			.phone(phone)
			.role(UserRole.ADMIN)
			.build();
	}

	/**
	 * ADMIN 전용 유저 생성
	 */
	public static User createUserWithRole(String email, String password, String nickName, String userName, String phone,
		UserRole role) {
		return User.builder()
			.email(email)
			.password(password)
			.nickName(nickName)
			.userName(userName)
			.phone(phone)
			.role(role)
			.build();
	}

	public void updateNotificationUrls(String slackUrl, String discordUrl) {
		this.slackUrl = slackUrl;
		this.discordUrl = discordUrl;
	}

	public void updateMyProfile(String nickName, String userName, String phone) {
		updateBasicInfo(nickName, userName, phone);
	}

	public void updateByAdmin(String nickName, String userName, String phone, UserRole role) {
		updateBasicInfo(nickName, userName, phone);
		if (role != null) {
			this.role = role;
		}
	}

	public boolean isDifferentNickName(String newNickName) {
		return newNickName != null && !newNickName.equals(this.nickName);
	}

	public void changePassword(String newEncodedPassword) {
		this.password = newEncodedPassword;
	}

	private void updateBasicInfo(String nickName, String userName, String phone) {
		if (nickName != null && !nickName.isBlank()) {
			this.nickName = nickName;
		}
		if (userName != null && !userName.isBlank()) {
			this.userName = userName;
		}
		if (phone != null && !phone.isBlank()) {
			this.phone = phone;
		}
	}
}