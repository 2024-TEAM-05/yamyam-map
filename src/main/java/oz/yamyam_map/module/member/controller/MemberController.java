package oz.yamyam_map.module.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.module.auth.jwt.JwtManager;
import oz.yamyam_map.module.member.dto.request.MemberSignupReq;
import oz.yamyam_map.module.member.dto.request.MemberUpdateReq;
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
	public BaseApiResponse<Void> postSignup(
		@RequestBody @Valid MemberSignupReq request) {
		memberService.postSignup(request);
		return BaseApiResponse.of(StatusCode.SIGN_UP_ACCEPTED);
	}

	@GetMapping("/{id}")
	@ResponseStatus(OK)
	public BaseApiResponse<MemberDetailRes> getMemberDetail(
		@PathVariable Long id,
		@RequestHeader("Authorization") String token) {

		Long memberId = extractMemberIdFromToken(token, id);

		MemberDetailRes memberDetail = memberService.getMemberDetail(memberId);

		return BaseApiResponse.of(StatusCode.OK, memberDetail);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(OK)
	public BaseApiResponse<Void> updateMember(
		@PathVariable Long id,
		@RequestHeader("Authorization") String token,
		@Valid @RequestBody MemberUpdateReq req) {

		Long memberId = extractMemberIdFromToken(token, id);

		memberService.updateMemberSettings(memberId, req);

		return BaseApiResponse.of(StatusCode.OK);
	}

	// 중복된 JWT 토큰 파싱 로직을 별도의 메서드로 추출
	private Long extractMemberIdFromToken(String token, Long id) {
		String jwtToken = token.substring(7);  // "Bearer " 부분을 제거
		Long memberId = jwtManager.getMemberId(jwtToken);  // 사용자 ID 추출

		// 경로의 ID와 JWT에서 추출한 ID가 일치하는지 검증
		if (!memberId.equals(id)) {
			throw new BusinessException(StatusCode.UNAUTHORIZED);
		}
		return memberId;
	}

}