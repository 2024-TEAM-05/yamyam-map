package oz.yamyam_map.batch.reader;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.batch.dto.SeoulRestaurantDto;
import oz.yamyam_map.batch.service.SeoulRestaurantApiService;

@Component
@RequiredArgsConstructor
public class RowSeoulDataApiReader implements ItemReader<SeoulRestaurantDto> {
	private static final int MAX_REQUEST_SIZE = 1000; // 서울 음식점 데이터는 한번에 1,000개만 요청 가능

	private final SeoulRestaurantApiService seoulRestaurantApiService;

	private int offset = 1;
	private boolean hasNext = true;
	private Queue<SeoulRestaurantDto> cache = new LinkedList<>();

	/**
	 * 네트워크 요청을 최대한 줄이기 위해, 미리 1000개의 데이터를 불러와 캐시한 뒤,
	 * 캐시된 데이터를 하나씩 반환! 캐시가 소비되면 다음 데이터부터 다시 1000개 요청!
	 */
	@Override
	public SeoulRestaurantDto read() {
		if (cache.isEmpty()) { // 캐시된 데이터가 모두 소비됐으면, 다음 데이터를 불러옴
			loadData();
		}

		if (!hasNext && cache.isEmpty()) { // 더 이상 데이터가 없으면 null 반환
			return null;
		}

		return cache.poll(); // 캐시에서 데이터를 1개 소비
	}

	private void loadData() {
		List<SeoulRestaurantDto> data = seoulRestaurantApiService.getRestaurants(offset, MAX_REQUEST_SIZE);

		if (data.isEmpty()) { // 받은 데이터가 비어있다면 더 이상 데이터가 없음을 표시 (모든 데이터를 처리)
			hasNext = false;
			return;
		}

		cache = new LinkedList<>(data);
		offset += MAX_REQUEST_SIZE;
	}
}
