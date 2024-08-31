package oz.yamyam_map.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.repository.RestaurantRepository;

@Component
@RequiredArgsConstructor
public class SeoulDataProcessor implements ItemProcessor<RowSeoulRestaurant, Restaurant> {

	private final RestaurantRepository rowSeoulRestaurantRepository;

	@Override
	public Restaurant process(RowSeoulRestaurant item) {
		// TODO: 데이터 전처리 작업 (폐업인 경우 기존 데이터 삭제, 업태 구분 설정, 위/경도 설정, 주소 설정, 시군구 매핑 등..)
		// TODO: RowSeoulRestaurant Restaurant 변환
		return null;
	}
}
