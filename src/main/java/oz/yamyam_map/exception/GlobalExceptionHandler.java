package oz.yamyam_map.exception;

import static org.springframework.http.HttpStatus.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.exception.custom.BadRequestException;
import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.exception.custom.DataNotFoundException;
import oz.yamyam_map.exception.custom.DuplicateResourceException;
import oz.yamyam_map.exception.custom.ForbiddenException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * BusinessException 및 하위 커스텀 예외 클래스에서 StatusCode 내보내는 메서드
	 **/
	private static StatusCode getStatusCodeFromException(BusinessException e) {
		HttpStatus httpStatus = e.getStatusCode().getHttpStatus();

		// 서버 에러인 경우 stack trace
		if (httpStatus.value() == 500) {
			e.printStackTrace();
		}

		return e.getStatusCode();
	}

	/**
	 * 요청이 잘못된 경우
	 * ex) 필수 입력 값을 전달하지 않은 경우
	 **/
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler({BadRequestException.class})
	public BaseApiResponse<Void> handleBadRequestException(BadRequestException e) {
		log.warn(e.getMessage(), e);

		return BaseApiResponse.of(getStatusCodeFromException(e));
	}

	/**
	 * 중복된 데이터 저장 요청을 정의한 에러인 경우
	 * ex) F 엔티티에 동일한 data를 넣은 경우
	 **/
	@ResponseStatus(CONFLICT)
	@ExceptionHandler({DuplicateResourceException.class})
	public BaseApiResponse<Void> handleDuplicateResourceException(DuplicateResourceException e) {
		log.warn(e.getMessage(), e);

		return BaseApiResponse.of(getStatusCodeFromException(e));
	}

	/**
	 * 접근할 수 없는 데이터 요청을 정의한 에러인 경우
	 * ex) 사용자가 관리자 권한의 api 요청을 한 경우
	 **/
	@ResponseStatus(FORBIDDEN)
	@ExceptionHandler({ForbiddenException.class})
	public BaseApiResponse<Void> handleForbiddenException(ForbiddenException e) {
		log.warn(e.getMessage(), e);

		return BaseApiResponse.of(getStatusCodeFromException(e));
	}

	/**
	 * 요청 시 필수 데이터이나, 전달하지 않은 요청을 정의한 에러인 경우
	 * ex) 게시물 id 전달했으나, 존재하지 않은 게시물인 경우
	 **/
	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler({DataNotFoundException.class})
	public BaseApiResponse<Void> handleDataNotFoundException(DataNotFoundException e) {
		log.warn(e.getMessage(), e);

		return BaseApiResponse.of(getStatusCodeFromException(e));
	}

	/**
	 * 비즈니스 로직 상 발생할 수 있는 정의한 에러인 경우
	 * ex) 하위 커스텀 외의 비즈니스 로직 예외인 경우
	 **/
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler({BusinessException.class})
	public BaseApiResponse<Void> handleBusinessException(BusinessException e) {
		log.warn(e.getMessage(), e);

		return BaseApiResponse.of(getStatusCodeFromException(e));
	}

	/**
	 * 유효성 검사 실패 에러 잡는 경우
	 * ex) validation 으로 설정해둔 NotNull 필드가 null로 온 경우
	 **/
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.warn(e.getMessage(), e);

		// validation 검사 실패 항목 보여주는 메세지
		String errors = convertToErrorResponses(e);

		return BaseApiResponse.of(BAD_REQUEST, errors);
	}

	/**
	 * 메서드 인자 타입이 맞지 않은 경우
	 * ex) Inteager type에 String type이 들어온 경우
	 **/
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public BaseApiResponse<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		log.warn(e.getMessage(), e);

		// 타입이 맞지 않은 메서드 인자 보여주는 메세지
		String message = formatMessageFrom(e);

		return BaseApiResponse.of(BAD_REQUEST, message);
	}

	/**
	 * JSON 파싱 시, 잘못된 형식의 데이터를 넣은 경우
	 * ex) enumType 에 해당하지 않은 항목이 들어온 경우
	 **/
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseApiResponse<Void> handleInvalidFormatException(InvalidFormatException e) {
		log.warn(e.getMessage(), e);

		// enum type 의 항목 보여주는 메세지
		String message = formatMessageFrom(e);

		return BaseApiResponse.of(BAD_REQUEST, message);
	}

	/**
	 * 정의하지 못한 내부 서버 에러인 경우
	 **/
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public BaseApiResponse<Void> handleUnknownException(Exception e) {
		log.error(e.getMessage(), e);

		StatusCode statusCode = StatusCode.findStatusCodeByNameSafe(e.getMessage(), StatusCode.INTERNAL_SERVER_ERROR);

		return BaseApiResponse.of(statusCode);
	}

	/**
	 * 인증 과정에서 사용자를 찾을 수 없는 경우 발생
	 * ex) JWT 토큰에 포함된 사용자 이름으로 사용자를 로드할 때 해당 사용자가 존재하지 않는 경우
	 **/
	@ResponseStatus(UNAUTHORIZED)
	@ExceptionHandler(UsernameNotFoundException.class)
	public BaseApiResponse<Void> handleUsernameNotFoundException(UsernameNotFoundException e) {
		log.warn(e.getMessage(), e);

		return BaseApiResponse.of(StatusCode.UNAUTHORIZED);
	}

	/**
	 * validation 검사 실패한 항목 에러 메세지 만드는 메서드
	 */
	private String convertToErrorResponses(MethodArgumentNotValidException e) {
		return e.getFieldErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.joining(", "));
	}

	/**
	 * 메서드 인자에 맞지 않은 자료형인 경우 에러 메세지 만드는 메서드
	 **/
	private String formatMessageFrom(MethodArgumentTypeMismatchException e) {
		String parameterName = e.getParameter().getParameterName();

		return parameterName + "의 타입이 잘못되었습니다.";
	}

	/**
	 * enumType에 해당하는 항목이 없는 경우 에러 메세지 만드는 메서드
	 **/
	private String formatMessageFrom(InvalidFormatException e) {
		Class<?> targetType = e.getTargetType();
		String enumTypeName = targetType.getSimpleName();
		String validValues = Arrays.stream(targetType.getEnumConstants())
			.map(enumConstant -> ((Enum<?>)enumConstant).name())
			.collect(Collectors.joining(", "));

		return enumTypeName + "는 [" + validValues + "] 중 하나여야 합니다.";
	}

}
