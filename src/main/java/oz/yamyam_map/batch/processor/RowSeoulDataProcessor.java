package oz.yamyam_map.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.batch.dto.SeoulRestaurantDto;
import oz.yamyam_map.batch.repository.RowSeoulRestaurantRepository;

@Component
@RequiredArgsConstructor
public class RowSeoulDataProcessor implements ItemProcessor<SeoulRestaurantDto, RowSeoulRestaurant> {

	private final RowSeoulRestaurantRepository rowSeoulRestaurantRepository;

	@Override
	public RowSeoulRestaurant process(SeoulRestaurantDto item) throws Exception {
		// TODO: 기존 row table을 조회한 뒤 변경/추가가 발생했는지 확인 (기존과 그대로라면 null을 반환하면 skip)
		// TODO: SeoulRestaurantDto를 RowSeoulRestaurant로 변환
		return null;
	}
}
