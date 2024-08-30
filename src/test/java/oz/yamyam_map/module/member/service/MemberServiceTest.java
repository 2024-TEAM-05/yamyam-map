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

import oz.yamyam_map.exception.custom.DataNotFoundException;
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
	@DisplayName("유효한 토큰으로 회원 정보를 정상적으로 가져오는 경우")
	void getMemberDetailSuccess() {
		// Given
		Long memberId = 1L;
		Member mockMember = Member.signUp("account", "password11!", passwordEncoder); // Mock Member 객체 생성

		when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

		// When
		MemberDetailRes result = memberService.getMemberDetail(memberId);

		// Then
		assertNotNull(result);
		assertThat(result.getAccount()).isEqualTo("account");

		verify(memberRepository, times(1)).findById(memberId);
	}

	@Test
	@DisplayName("회원이 존재하지 않을 때 DataNotFoundException 발생")
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
}
