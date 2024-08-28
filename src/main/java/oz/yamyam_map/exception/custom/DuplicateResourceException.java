package oz.yamyam_map.exception.custom;

import lombok.Getter;
import oz.yamyam_map.common.code.StatusCode;

/**
 * 생성하고자 요청하는 데이터가 이미 있는 경우
 * ex) Http Status 409
 **/
@Getter
public class DuplicateResourceException extends BusinessException {

	public DuplicateResourceException(StatusCode statusCode) {
		super(statusCode);
	}

}