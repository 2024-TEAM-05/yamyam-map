package oz.yamyam_map.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.batch.repository.RowSeoulRestaurantRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RowSeoulDataWriter implements ItemWriter<RowSeoulRestaurant> {

	private final RowSeoulRestaurantRepository rowSeoulRestaurantRepository;

	/**
	 * chunk개의 데이터가 처리되면 한번에 row_seoul_restaunt 테이블로 write
	 */
	@Override
	public void write(Chunk<? extends RowSeoulRestaurant> chunk) {
		rowSeoulRestaurantRepository.saveAllInBatch(chunk);
		log.info("{} 개의 데이터를 ROW 테이블에 저장했습니다...", chunk.size());
	}
}
