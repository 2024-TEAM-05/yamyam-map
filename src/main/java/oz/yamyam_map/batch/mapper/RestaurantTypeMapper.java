package oz.yamyam_map.batch.mapper;

import java.util.HashMap;
import java.util.Map;

import oz.yamyam_map.common.enums.RestaurantType;

public class RestaurantTypeMapper {
	private static final Map<String, RestaurantType> categoryMap = new HashMap<>();
	static {
		// 한식
		categoryMap.put("경양식", RestaurantType.KOREAN_FOOD);
		categoryMap.put("김밥(도시락)", RestaurantType.KOREAN_FOOD);
		categoryMap.put("냉면집", RestaurantType.KOREAN_FOOD);
		categoryMap.put("복어취급", RestaurantType.KOREAN_FOOD);
		categoryMap.put("분식", RestaurantType.KOREAN_FOOD);
		categoryMap.put("식육(숯불구이)", RestaurantType.KOREAN_FOOD);
		categoryMap.put("탕류(보신용)", RestaurantType.KOREAN_FOOD);
		categoryMap.put("통닭(치킨)", RestaurantType.KOREAN_FOOD);
		categoryMap.put("한식", RestaurantType.KOREAN_FOOD);

		// 중식
		categoryMap.put("중국식", RestaurantType.CHINESE_FOOD);

		// 양식
		categoryMap.put("외국음식전문점(인도,태국등)", RestaurantType.WESTERN_FOOD);
		categoryMap.put("패밀리레스토랑", RestaurantType.WESTERN_FOOD);
		categoryMap.put("패스트푸드", RestaurantType.WESTERN_FOOD);

		// 일식
		categoryMap.put("일식", RestaurantType.JAPANESE_FOOD);
		categoryMap.put("횟집", RestaurantType.JAPANESE_FOOD);

		// 카페
		categoryMap.put("까페", RestaurantType.CAFE);
		categoryMap.put("다방", RestaurantType.CAFE);
		categoryMap.put("전통찻집", RestaurantType.CAFE);
		categoryMap.put("제과점영업", RestaurantType.CAFE);
		categoryMap.put("커피숍", RestaurantType.CAFE);
		categoryMap.put("키즈카페", RestaurantType.CAFE);

		// 기타
		categoryMap.put("기타", RestaurantType.ELSE);
		categoryMap.put("기타 휴게음식점", RestaurantType.ELSE);
		categoryMap.put("뷔페식", RestaurantType.ELSE);
		categoryMap.put("식품등 수입판매업", RestaurantType.ELSE);
		categoryMap.put("식품소분업", RestaurantType.ELSE);
		categoryMap.put("이동조리", RestaurantType.ELSE);
		categoryMap.put("일반조리판매", RestaurantType.ELSE);
		categoryMap.put("출장조리", RestaurantType.ELSE);

		// 술집
		categoryMap.put("대포집/소주방", RestaurantType.BAR);
		categoryMap.put("간이주점", RestaurantType.BAR);
		categoryMap.put("감성주점", RestaurantType.BAR);
		categoryMap.put("라이브카페", RestaurantType.BAR);
		categoryMap.put("룸살롱", RestaurantType.BAR);
		categoryMap.put("정종/대포집/소주방", RestaurantType.BAR);
		categoryMap.put("호프/통닭", RestaurantType.BAR);

		// None
		categoryMap.put("Null", RestaurantType.NONE);
		categoryMap.put("", RestaurantType.NONE);
	}

	public static RestaurantType from(String subCategory) {
		return categoryMap.getOrDefault(subCategory, RestaurantType.NONE);
	}
}
