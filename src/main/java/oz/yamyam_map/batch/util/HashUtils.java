package oz.yamyam_map.batch.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class HashUtils {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final String HASH_ALGORITHM = "SHA-256";

	public static String sha256FromObject(Object obj) {
		try {
			String json = objectMapper.writeValueAsString(obj); // 객체를 JSON 문자열로 직렬화
			return sha256(json);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("객체를 직렬화 하는데 실패 했습니다.", e);
		}
	}

	private static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM); // MessageDigest는 해싱 알고리즘을 구현한 클래스
			byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8)); // 입력 문자열 base를 해싱

			return bytesToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// 바이트 배열을 16진수 문자열로 변환하는 메서드
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);

		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);

			if (hex.length() == 1) {
				hexString.append('0');
			}

			hexString.append(hex);
		}

		return hexString.toString();
	}
}
