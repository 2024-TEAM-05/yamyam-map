package oz.yamyam_map.module.member.service;

import static oz.yamyam_map.common.code.StatusCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.exception.custom.DuplicateResourceException;
import oz.yamyam_map.module.auth.jwt.JwtManager;
import oz.yamyam_map.module.member.dto.MemberSignupReq;
import oz.yamyam_map.module.member.entity.Member;
import oz.yamyam_map.module.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final JwtManager jwtManager;

	@Transactional
	public void signUp(MemberSignupReq request) {
		// 계정 중복 검사
		validateAccountUnique(request.account());

		// 회원 생성 및 저장
		Member member = Member.signUp(request.account(), request.password(), passwordEncoder);
		memberRepository.save(member);

	}

	private void validateAccountUnique(String account) {
		if (memberRepository.existsByAccount(account)) {
			throw new DuplicateResourceException(DUPLICATE_ACCOUNT);
		}
	}

}
