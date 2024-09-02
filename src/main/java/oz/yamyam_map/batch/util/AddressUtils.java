package oz.yamyam_map.batch.util;

public class AddressUtils {
	public static String extractCityDistrict(String address) {
		// 주소가 null 또는 빈 문자열인지 확인
		if (address == null || address.trim().isEmpty()) {
			return null;
		}

		// 주소를 공백으로 분리
		String[] words = address.split("\\s+");

		// 1번째 단어 추출
		if (words.length > 1) {
			return words[1]; // 군, 구 반환
		}

		return null;
	}
}
