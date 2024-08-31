package oz.yamyam_map.common.util;

import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.module.auth.security.CustomUserDetails;

/**
 * 사용자가 특정 리소스에 접근 권한이 있는지 검증
 */
public class PermissionValidator {

	public static void validateMemberId(CustomUserDetails customUserDetails, Long id) {
		if (!customUserDetails.getMemberId().equals(id)) {
			throw new BusinessException(StatusCode.FORBIDDEN_RESOURCE_ACCESS);
		}
	}

}
