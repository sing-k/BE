package com.project.singk.domain.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailDomain {
	NAVER("naver.com"),
	GOOGLE("gmail.com");
	private final String domain;

	public boolean equals(String email) {
		int idx = email.indexOf('@');
		if (idx != - 1) {
			return this.domain.equals(email.substring(idx + 1));
		}
		return false;
	}
}
