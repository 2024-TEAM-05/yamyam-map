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
	public RowSeoulRestaurant process(SeoulRestaurantDto item) {
		return rowSeoulRestaurantRepository.findByMgtno(item.mgtno())
			.map(existingRecord -> updateIfChanged(existingRecord, item))
			.orElseGet(() -> RowSeoulRestaurant.of(item));
	}

	private RowSeoulRestaurant updateIfChanged(RowSeoulRestaurant existingRecord, SeoulRestaurantDto newItem) {
		if (existingRecord.hasNotChanged(newItem)) {
			return null; // 변경 사항이 없기 때문에 SKIP
		}

		return existingRecord.update(newItem);
	}
}
