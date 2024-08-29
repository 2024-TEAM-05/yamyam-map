package oz.yamyam_map.module.member.controller;

import static org.springframework.http.HttpStatus.*;
import static oz.yamyam_map.common.code.StatusCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.module.member.dto.MemberSignupReq;
import oz.yamyam_map.module.member.service.MemberService;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

	private final MemberService memberService;

	@PostMapping
	@ResponseStatus(ACCEPTED)
	public BaseApiResponse<Void> signUp(@RequestBody @Valid MemberSignupReq request) {
		memberService.signUp(request);
		return BaseApiResponse.of(SIGN_UP_ACCEPTED);
	}

}
