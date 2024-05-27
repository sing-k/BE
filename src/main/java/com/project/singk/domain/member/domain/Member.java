package com.project.singk.domain.member.domain;

import java.time.LocalDateTime;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBERS")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private String password;

	private String imageUrl;

	@Column(unique = true)
	private String nickname;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String name;

	private LocalDateTime birthday;

	@Enumerated(EnumType.STRING)
	private Role role;

	public void updateImage(String key) {
		this.imageUrl = key;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}
}
