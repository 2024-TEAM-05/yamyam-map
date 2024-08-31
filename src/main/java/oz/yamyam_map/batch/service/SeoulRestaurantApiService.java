package oz.yamyam_map.batch.service;

import java.util.List;

import org.springframework.stereotype.Component;

import oz.yamyam_map.batch.dto.SeoulRestaurantDto;

@Component
public class SeoulRestaurantApiService {

	public List<SeoulRestaurantDto> getRestaurants(int offset, int limit) {
		/**
		 * TODO: HTTP 요청을 보내고, 결과를 SeoulRestaurantDto 리스트로 변환
		 *  - GET http://openapi.seoul.go.kr:8088/{개발자키}/json/LOCALDATA_072404/{start}/{end}/
		 *  - offset이 데이터 범위를 넘어설 경우 json으로 파싱이 안될 것임 (예외를 잡아서 처리하거나 다른 추가 로직 필요)
		 *  - http 요청 시 start부터 end까지의 데이터를 가져옴 (offset/limit가 아님으로 주의!)
		 */
		return List.of();
	}
}
