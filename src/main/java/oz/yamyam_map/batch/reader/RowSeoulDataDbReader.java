package oz.yamyam_map.batch.reader;

import static java.util.Objects.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.batch.repository.RowSeoulRestaurantRepository;

@Component
@StepScope
public class RowSeoulDataDbReader implements ItemReader<RowSeoulRestaurant> {

	private final RowSeoulRestaurantRepository restaurantRepository;
	private Iterator<RowSeoulRestaurant> rowSeoulRestaurantIterator;

	/**
	 * 업데이트 날짜가 jobParameters의 date와 같으면 변경/추가된 데이터이므로 처리 대상!
	 * 참고) 서울 음식점 관련 데이터 파이프라인은 하루에 딱 1번 발생
	 */
	public RowSeoulDataDbReader(RowSeoulRestaurantRepository billingDataRepository,
		@Value("#{jobParameters['date']}") LocalDate date) {
		this.restaurantRepository = billingDataRepository;
		List<RowSeoulRestaurant> billingDataList = restaurantRepository.findAllByUpdatedAt(date);
		this.rowSeoulRestaurantIterator = billingDataList.iterator();
	}

	@Override
	public RowSeoulRestaurant read() {
		if (nonNull(rowSeoulRestaurantIterator) && rowSeoulRestaurantIterator.hasNext()) {
			return rowSeoulRestaurantIterator.next();
		} else {
			return null;
		}
	}
}
