package oz.yamyam_map.module.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.common.util.PermissionValidator;
import oz.yamyam_map.module.auth.security.CustomUserDetails;
import oz.yamyam_map.module.member.dto.request.MemberSignupReq;
import oz.yamyam_map.module.member.dto.request.MemberUpdateReq;
import oz.yamyam_map.module.member.dto.response.MemberDetailRes;
import oz.yamyam_map.module.member.service.MemberService;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

	private final MemberService memberService;

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
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		PermissionValidator.validateMemberId(customUserDetails, id);

		MemberDetailRes memberDetail = memberService.getMemberDetail(id);

		return BaseApiResponse.of(StatusCode.OK, memberDetail);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(OK)
	public BaseApiResponse<Void> updateMember(
		@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@Valid @RequestBody MemberUpdateReq req) {

		PermissionValidator.validateMemberId(customUserDetails, id);

		memberService.updateMemberSettings(id, req);

		return BaseApiResponse.of(StatusCode.OK);
	}

}