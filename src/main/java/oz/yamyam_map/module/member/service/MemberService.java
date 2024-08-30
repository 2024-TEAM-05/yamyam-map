package oz.yamyam_map.module.member.service;

import static oz.yamyam_map.common.code.StatusCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.exception.custom.DataNotFoundException;
import oz.yamyam_map.exception.custom.DuplicateResourceException;
import oz.yamyam_map.module.member.dto.request.MemberSignupReq;
import oz.yamyam_map.module.member.dto.request.MemberUpdateReq;
import oz.yamyam_map.module.member.dto.response.MemberDetailRes;
import oz.yamyam_map.module.member.entity.Member;
import oz.yamyam_map.module.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	@Transactional
	public void postSignup(MemberSignupReq request) {
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

	public MemberDetailRes getMemberDetail(Long memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));

		return MemberDetailRes.fromEntity(member);

	}

	@Transactional
	public void updateMemberSettings(Long memberId, MemberUpdateReq req) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));

		// 위도와 경도가 모두 제공된 경우에만 위치 정보 업데이트
		if (req.getLatitude() != null && req.getLongitude() != null) {
			member.updateLocation(req.getLatitude(), req.getLongitude());
		} else if (req.getLatitude() != null || req.getLongitude() != null) {
			// 위도 또는 경도 중 하나만 제공된 경우 예외 처리
			throw new BusinessException(MISSING_LATITUDE_OR_LONGITUDE);
		}

		// 알림 활성화 유무 업데이트
		if (req.getReceiveRecommendations() != null) {
			member.updateReceiveRecommendations(req.getReceiveRecommendations());
		}

	}

}
