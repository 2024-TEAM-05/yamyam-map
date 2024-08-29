package oz.yamyam_map.module.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MemberSignupReq(
	@NotBlank(message = "계정은 필수값입니다.")
	@Schema(description = "계정", example = "account")
	String account,

	@NotBlank(message = "비밀번호는 필수값입니다.")
	@Schema(description = "비밀번호", example = "password00!")
	String password
) {
}
