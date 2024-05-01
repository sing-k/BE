package com.project.singk.domain.member.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;

	private String password;

	private String imageUrl;

	private String nickname;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String name;

	private LocalDateTime birthday;
}
