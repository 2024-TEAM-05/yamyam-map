package oz.yamyam_map.module.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.module.member.dto.request.MemberSignupReq;
import oz.yamyam_map.module.member.dto.response.MemberDetailRes;

@Tag(name = "Member", description = "멤버 관련 API")
public interface MemberControllerDocs {

	@Operation(summary = "회원가입")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "202", description = "회원가입이 완료되었습니다.", useReturnTypeSchema = true)
	})
	BaseApiResponse<Void> signUp(MemberSignupReq request);

	@Operation(summary = "사용자 정보 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공했습니다.", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "404", description = "요청된 사용자를 찾을 수 없습니다.", useReturnTypeSchema = true)
	})
	BaseApiResponse<MemberDetailRes> getMemberDetail(String token);
}
