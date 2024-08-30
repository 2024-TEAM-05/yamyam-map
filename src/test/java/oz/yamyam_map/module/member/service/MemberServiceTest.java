package oz.yamyam_map.module.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.exception.custom.DataNotFoundException;
import oz.yamyam_map.module.member.dto.request.MemberUpdateReq;
import oz.yamyam_map.module.member.dto.response.MemberDetailRes;
import oz.yamyam_map.module.member.entity.Member;
import oz.yamyam_map.module.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private MemberService memberService;

	@Test
	@DisplayName("[성공] 유효한 토큰으로 회원 정보를 정상적으로 가져오는 경우")
	void getMemberDetailSuccess() {
		// Given
		Long memberId = 1L;
		Member mockMember = Member.signUp("account", "password11!", passwordEncoder); // Mock Member 객체 생성

		when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

		// When
		MemberDetailRes result = memberService.getMemberDetail(memberId);

		// Then
		assertNotNull(result);
		assertEquals("account", result.getAccount());

		verify(memberRepository, times(1)).findById(memberId);
	}

	@Test
	@DisplayName("[실패] 회원이 존재하지 않을 때 DataNotFoundException 발생")
	void getMemberDetailThrowsExceptionWhenMemberNotFound() {
		// Given
		Long memberId = 1L;

		when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

		// When & Then
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
			memberService.getMemberDetail(memberId)
		);

		verify(memberRepository, times(1)).findById(memberId);
	}

	@Test
	@DisplayName("[성공] 성공적으로 위치 정보와 알림 설정을 업데이트")
	void updateMemberSettingsSuccess() {
		// Given
		Long memberId = 1L;
		Member mockMember = mock(Member.class);
		MemberUpdateReq req = new MemberUpdateReq(37.123, 127.123, true);  // 위도, 경도, 알림 설정 제공

		when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

		// When
		memberService.updateMemberSettings(memberId, req);

		// Then
		verify(mockMember, times(1)).updateLocation(37.123, 127.123);
		verify(mockMember, times(1)).updateReceiveRecommendations(true);
	}

	@Test
	@DisplayName("[실패] 위도만 제공된 경우 BusinessException 발생")
	void updateMemberSettingsThrowsExceptionWhenOnlyLatitudeProvided() {
		// Given
		Long memberId = 1L;
		Member mockMember = mock(Member.class);
		MemberUpdateReq req = new MemberUpdateReq(37.123, null, null);  // 경도 누락

		when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

		// When & Then
		assertThatThrownBy(() -> memberService.updateMemberSettings(memberId, req))
			.isInstanceOf(BusinessException.class)
			.hasMessageContaining("위도와 경도는 동시에 제공되어야 합니다.");

		verify(mockMember, never()).updateLocation(anyDouble(), anyDouble());
		verify(mockMember, never()).updateReceiveRecommendations(anyBoolean());
	}

	@Test
	@DisplayName("[실패] 경도만 제공된 경우 BusinessException 발생")
	void updateMemberSettingsThrowsExceptionWhenOnlyLongitudeProvided() {
		// Given
		Long memberId = 1L;
		Member mockMember = mock(Member.class);
		MemberUpdateReq req = new MemberUpdateReq(null, 127.123, null);  // 위도 누락

		when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

		// When & Then
		assertThatThrownBy(() -> memberService.updateMemberSettings(memberId, req))
			.isInstanceOf(BusinessException.class)
			.hasMessageContaining("위도와 경도는 동시에 제공되어야 합니다.");

		verify(mockMember, never()).updateLocation(anyDouble(), anyDouble());
		verify(mockMember, never()).updateReceiveRecommendations(anyBoolean());
	}

	@Test
	@DisplayName("[실패] 회원이 존재하지 않는 경우 DataNotFoundException 발생")
	void updateMemberSettingsThrowsExceptionWhenMemberNotFound() {
		// Given
		Long memberId = 1L;
		MemberUpdateReq req = new MemberUpdateReq(37.123, 127.123, true);  // 정상 요청

		when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> memberService.updateMemberSettings(memberId, req))
			.isInstanceOf(DataNotFoundException.class)
			.hasMessageContaining("요청된 사용자를 찾을 수 없습니다.");

		verify(memberRepository, times(1)).findById(memberId);
	}
}
