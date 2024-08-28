package oz.yamyam_map.common;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import oz.yamyam_map.common.code.StatusCode;

/**
 * code, status, message 기본 응답 형식
 **/
@Getter
@Schema(description = "기본 응답 형식")
public class ApiResponse<T> {
	@Schema(description = "응답 데이터")
	private final T data;
	@Schema(description = "HTTP 상태 코드", example = "200")
	private final int code;
	@Schema(description = "HTTP 상태", example = "OK")
	private final HttpStatus status;
	@Schema(description = "응답 메세지", example = "요청이 성공했습니다.")
	private final String message;

	public ApiResponse(HttpStatus status, String message, T data) {
		this.code = status.value();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	// 상태, 메세지 정의해주는 경우
	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
		return new ApiResponse<>(httpStatus, message, data);
	}

	// 상태만 정의, 메세지는 디폴트 메세지 사용하는 경우
	public static ApiResponse<Void> of(HttpStatus httpStatus) {
		return of(httpStatus, HttpStatus.valueOf(httpStatus.value()).getReasonPhrase(), null);
	}

	// 상태, 메세지만 정의하는 경우
	public static ApiResponse<Void> of(HttpStatus httpStatus, String message) {
		return of(httpStatus, message, null);
	}

	// 상태 코드로 정의하는 경우
	public static ApiResponse<Void> of(StatusCode statusCode) {
		return of(statusCode.getHttpStatus(), statusCode.getMessage(), null);
	}

	// 상태, 데이터를 통해 정의하는 경우
	public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
		return of(httpStatus, HttpStatus.valueOf(httpStatus.value()).getReasonPhrase(), data);
	}

	// 상태 코드, 데이터를 통해 정의하는 경우
	public static <T> ApiResponse<T> of(StatusCode statusCode, T data) {
		return of(statusCode.getHttpStatus(), statusCode.getMessage(), data);
	}
}
