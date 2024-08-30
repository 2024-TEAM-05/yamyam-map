package oz.yamyam_map.module.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.module.auth.jwt.JwtManager;
import oz.yamyam_map.module.member.dto.request.MemberSignupReq;
import oz.yamyam_map.module.member.dto.response.MemberDetailRes;
import oz.yamyam_map.module.member.service.MemberService;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

	private final MemberService memberService;
	private final JwtManager jwtManager;

	@PostMapping
	@ResponseStatus(ACCEPTED)
	public BaseApiResponse<Void> signUp(
		@RequestBody @Valid MemberSignupReq request) {
		memberService.signUp(request);
		return BaseApiResponse.of(StatusCode.SIGN_UP_ACCEPTED);
	}

	@GetMapping("/detail")
	@ResponseStatus(OK)
	public BaseApiResponse<MemberDetailRes> getMemberDetail(
		@RequestHeader("Authorization") String token) {

		// 토큰에서 "Bearer " 부분을 제거하고 JWT 토큰만 추출
		String jwtToken = token.substring(7);

		// JWT 토큰에서 사용자 ID를 추출
		Long memberId = jwtManager.getMemberId(jwtToken);

		MemberDetailRes memberDetail = memberService.getMemberDetail(memberId);

		return BaseApiResponse.of(StatusCode.OK, memberDetail);
	}

}
