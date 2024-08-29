package oz.yamyam_map.module.member.vo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static oz.yamyam_map.common.code.StatusCode.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import oz.yamyam_map.exception.custom.BadRequestException;

@ExtendWith(MockitoExtension.class)
class PasswordTest {

	private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

	@ParameterizedTest
	@ValueSource(strings = {"short", "123456789", "abcdefghij"})
	@DisplayName("비밀번호가 조건을 충족하지 않을 때 예외 발생")
	void invalidPasswordThrowsException(String invalidPassword) {
		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			Password.of(invalidPassword, passwordEncoder);
		});

		// 여기서는 어떤 예외가 발생했는지 구체적으로 테스트할 수 있습니다.
		// 예: 짧은 비밀번호 또는 단순한 비밀번호에 대한 예외 처리 확인
		if (invalidPassword.length() < 10) {
			assertEquals(SHORT_PASSWORD, exception.getStatusCode());
		} else if (invalidPassword.matches("^\\d+$") || invalidPassword.matches("^[a-zA-Z]+$")) {
			assertEquals(SIMPLE_PASSWORD, exception.getStatusCode());
		} else {
			fail("Unexpected exception for password: " + invalidPassword);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"validPass123!", "AnotherPass456$", "Complex!@#789"})
	@DisplayName("유효한 비밀번호는 예외 없이 생성되어야 한다")
	void validPasswordDoesNotThrowException(String validPassword) {
		when(passwordEncoder.encode(validPassword)).thenReturn("encodedPassword");

		assertDoesNotThrow(() -> {
			Password password = Password.of(validPassword, passwordEncoder);
			assertEquals("encodedPassword", password.getValue());
		});
	}

	@ParameterizedTest
	@ValueSource(strings = {"aaa1234567", "bbb!@#1111", "ccc2222$$$"})
	@DisplayName("비밀번호에 3회 이상 연속된 문자가 포함되면 예외 발생")
	void repeatingCharactersPasswordThrowsException(String repeatingPassword) {
		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			Password.of(repeatingPassword, passwordEncoder);
		});

		assertEquals(PASSWORD_HAS_REPEATING_CHARACTER, exception.getStatusCode());
	}

}