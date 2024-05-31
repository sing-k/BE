package com.project.singk.domain.member.infrastructure;

import java.time.LocalDateTime;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;
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
@Table(name = "MEMBERS")
@Getter
@NoArgsConstructor
public class MemberEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "nickname", unique = true)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@Column(name = "name")
	private String name;

	@Column(name = "birthday")
	private LocalDateTime birthday;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Builder
	public MemberEntity(Long id, String email, String password, String imageUrl, String nickname, Gender gender,
		String name, LocalDateTime birthday, Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.imageUrl = imageUrl;
		this.nickname = nickname;
		this.gender = gender;
		this.name = name;
		this.birthday = birthday;
		this.role = role;
	}

	public static MemberEntity from (Member member) {
		return MemberEntity.builder()
			.id(member.getId())
			.email(member.getEmail())
			.password(member.getPassword())
			.imageUrl(member.getImageUrl())
			.nickname(member.getNickname())
			.gender(member.getGender())
			.name(member.getName())
			.birthday(member.getBirthday())
			.role(member.getRole())
			.build();
	}

	public Member toModel() {
		return Member.builder()
			.id(this.id)
			.email(this.email)
			.password(this.getPassword())
			.imageUrl(this.getImageUrl())
			.nickname(this.getNickname())
			.gender(this.getGender())
			.name(this.getName())
			.birthday(this.getBirthday())
			.role(this.getRole())
			.createdAt(this.getCreatedAt())
			.modifiedAt(this.getModifiedAt())
			.build();
	}
}
