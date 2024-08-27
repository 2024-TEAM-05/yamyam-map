package oz.yamyam_map.exception.custom;

import lombok.Getter;
import oz.yamyam_map.common.code.StatusCode;

/**
 * 요청 결과가 없는 경우
 * ex) Http Status 404
 **/
@Getter
public class DataNotFoundException extends BusinessException {

	public DataNotFoundException(StatusCode statusCode) {
		super(statusCode);
	}

}