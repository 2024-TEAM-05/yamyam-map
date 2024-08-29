package oz.yamyam_map.common.enums;

public enum RestaurantType {
	KOREAN_FOOD("한식"),
	CHINESE_FOOD("중식"),
	JAPANESE_FOOD("일식"),
	WESTERN_FOOD("양식"),
	CAFE("카페"),
	BAR("주점"),
	ELSE("기타"),
	NONE("알수없음");

	private final String value;

	RestaurantType(String value) {
		this.value = value;
	}
}
