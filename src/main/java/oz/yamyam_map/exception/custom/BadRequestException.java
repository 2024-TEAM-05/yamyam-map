package oz.yamyam_map.exception.custom;

import lombok.Getter;
import oz.yamyam_map.common.code.StatusCode;

/**
 * 요청이 잘못된 경우
 * ex) Http status 400
 **/
@Getter
public class BadRequestException extends BusinessException {

	public BadRequestException(StatusCode statusCode) {
		super(statusCode);
	}

}