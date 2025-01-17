package com.project.singk.global.api;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppHttpStatus {
	/**
	 * 20X : 성공
	 */
	OK(200, HttpStatus.OK, "요청이 정상적으로 수행되었습니다."),
	CREATED(201, HttpStatus.CREATED, "리소스를 생성하였습니다."),

	/**
	 * 400 : 잘못된 문법으로 인해 요청을 이해할 수 없음
	 */
	BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	FAILED_VALIDATION(400, HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다."),
	FAILED_VERIFY_CERTIFICATION_CODE(400, HttpStatus.BAD_REQUEST, "인증 코드가 만료되었거나 일치하지 않습니다."),
	FAILED_LOGIN(400, HttpStatus.BAD_REQUEST, "아이디와 비밀번호를 정확히 입력해 주세요."),
	NOT_SUPPORT_EMAIL_FORMAT(400, HttpStatus.BAD_REQUEST, "지원하지 않는 이메일 형식입니다."),
    NOT_MATCH_ALBUM_REVIEW_VOTE_TYPE(400, HttpStatus.BAD_REQUEST, "공감/비공감과 저장되어 있는 공감/비공감이 일치하지 않습니다."),
	INVALID_OAUTH_TYPE(400, HttpStatus.BAD_REQUEST, "유효하지 않은 OAuth 타입입니다."),
	INVALID_ALBUM_REVIEW_VOTER(400, HttpStatus.BAD_REQUEST, "유효하지 않은 앨범 감상평 공감/비공감 입니다."),
	INVALID_POST_LIKES(400, HttpStatus.BAD_REQUEST, "유효하지 않은 게시글 공감 입니다."),
	INVALID_COMMENT_LIKES(400, HttpStatus.BAD_REQUEST, "유효하지 않은 댓글 공감 입니다."),
	INVALID_FILE(400, HttpStatus.BAD_REQUEST, "유효하지 않은 파일입니다."),
    DUPLICATE_NICKNAME(400, HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임 입니다."),
    DUPLICATE_ALBUM_REVIEW_VOTE(400, HttpStatus.BAD_REQUEST, "이미 공감/비공감한 앨범 감상평입니다."),
    DUPLICATE_ALBUM_REVIEW(400, HttpStatus.BAD_REQUEST, "이미 감상평을 작성 앨범입니다."),
    DUPLICATE_MEMBER(400, HttpStatus.BAD_REQUEST, "이미 가입한 회원 입니다."),
    DUPLICATE_POST_LIKES(400, HttpStatus.BAD_REQUEST, "이미 공감한 게시글 입니다."),
    DUPLICATE_COMMENT_LIKES(400, HttpStatus.BAD_REQUEST, "이미 공감한 댓글 입니다."),
	/**
	 * 401 : 인증된 사용자가 아님
	 */
	UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
	INVALID_TOKEN(401, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	MALFORMED_TOKEN(401, HttpStatus.UNAUTHORIZED, "손상된 토큰입니다."),
	EXPIRED_TOKEN(401, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	UNSUPPORTED_TOKEN(401, HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
	BLOCKED_TOKEN(401, HttpStatus.UNAUTHORIZED, "로그아웃 처리된 토큰입니다."),
	OAUTH_UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "OAuth 로그인에 실패 했습니다."),

	/**
	 * 403 : 접근 권한이 없음
	 */
	FORBIDDEN(403, HttpStatus.FORBIDDEN, "권한이 없습니다."),
	FORBIDDEN_ALBUM_REVIEW(403, HttpStatus.FORBIDDEN, "해당 앨범 감상평에 대한 접근 권한이 없습니다."),
	FORBIDDEN_POST(403, HttpStatus.FORBIDDEN, "해당 게시글에 대한 접근 권한이 없습니다."),
	FORBIDDEN_COMMENT(403, HttpStatus.FORBIDDEN, "해당 댓글에 대한 접근 권한이 없습니다."),
	/**
	 * 404 : 응답할 리소스가 없음
	 */
	NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
    NOT_FOUND_POST(404, HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    NOT_FOUND_POST_LIKES(404, HttpStatus.NOT_FOUND, "존재하지 않는 게시글 공감입니다."),
    NOT_FOUND_COMMENT(404, HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    NOT_FOUND_COMMENT_LIKES(404, HttpStatus.NOT_FOUND, "존재하지 않는 댓글 공감입니다."),
	NOT_FOUND_MEMBER(404, HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
	NOT_FOUND_ALBUM(404, HttpStatus.NOT_FOUND, "존재하지 않는 앨범입니다."),
    NOT_FOUND_ALBUM_GENRE(404, HttpStatus.NOT_FOUND, "존재하지 않는 장르입니다."),
	NOT_FOUND_ALBUM_REVIEW(404, HttpStatus.NOT_FOUND, "존재하지 않는 앨범 감상평입니다."),
	NOT_FOUND_ALBUM_REVIEW_VOTE(404, HttpStatus.NOT_FOUND, "존재하지 않는 앨범 감상평 공감/비공감 입니다."),
	NOT_FOUND_PROFILE_IMAGE(404, HttpStatus.NOT_FOUND, "등록되지 않거나 존재하지 않는 이미지 입니다."),

	/**
	 * 500 : 서버 내부에서 에러가 발생함
	 */
	INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에 에러가 발생했습니다."),
	FAILED_SEND_MAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패했습니다"),
	FAILED_AUTHENTICATION_SPOTIFY(500, HttpStatus.INTERNAL_SERVER_ERROR, "Spotify API 인증에 실패했습니다."),
	FAILED_AUTHENTICATION_OAUTH(500, HttpStatus.INTERNAL_SERVER_ERROR, "OAuth 인증에 실패했습니다."),
	FAILED_REQUEST_SPOTIFY(500, HttpStatus.INTERNAL_SERVER_ERROR, "Spotify API 요청에 실패했습니다."),
	FAILED_REQUEST_S3(500, HttpStatus.INTERNAL_SERVER_ERROR, "S3 요청에 실패했습니다."),
	FAILED_IO(500, HttpStatus.INTERNAL_SERVER_ERROR, "IO 작업에 실패했습니다.");

	private final int statusCode;
	private final HttpStatus httpStatus;
	private final String message;
}
