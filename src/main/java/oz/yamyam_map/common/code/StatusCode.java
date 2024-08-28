package oz.yamyam_map.common.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum StatusCode {

	/**
	 * 200 번대 CODE
	 **/
	OK(HttpStatus.OK, "요청이 성공했습니다."),
	CREATED(HttpStatus.CREATED, "생성되었습니다."),
	SIGN_UP_ACCEPTED(HttpStatus.ACCEPTED, "회원가입이 완료되었습니다. 이메일 인증을 진행해 주세요."),

	/**
	 * 400 번대 CODE
	 **/
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 리소스를 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "요청 경로가 지원되지 않습니다."),
	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
	SHORT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 최소 10자 이상이어야 합니다."),
	SIMPLE_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 숫자, 문자, 특수문자 중 2가지 이상을 포함해야 합니다."),
	PASSWORD_HAS_REPEATING_CHARACTER(HttpStatus.BAD_REQUEST, "비밀번호에 3회 이상 연속되는 문자 사용이 불가합니다."),
	DUPLICATE_ACCOUNT(HttpStatus.CONFLICT, "이미 사용 중인 계정입니다."),
	DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "요청된 사용자를 찾을 수 없습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 오류가 발생했습니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

	/**
	 * 500 번대 CODE
	 **/
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	StatusCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	/**
	 * 이름으로 StatusCode 찾고, 없다면 defaultStatusCode 로 정의하는 함수
	 **/
	public static StatusCode findStatusCodeByNameSafe(String name, StatusCode defaultStatusCode) {
		try {
			return StatusCode.valueOf(name);
		} catch (IllegalArgumentException e) {
			return defaultStatusCode;
		}
	}
}
