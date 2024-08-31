package oz.yamyam_map.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.repository.RestaurantRepository;

@Component
@RequiredArgsConstructor
public class SeoulDataWriter implements ItemWriter<Restaurant> {

	private final RestaurantRepository restaurantRepository;

	/**
	 * chunk개의 데이터가 처리되면 한번에 restaurant 테이블로 write
	 */
	@Override
	public void write(Chunk<? extends Restaurant> chunk) throws Exception {
		restaurantRepository.saveAll(chunk);
	}
}
