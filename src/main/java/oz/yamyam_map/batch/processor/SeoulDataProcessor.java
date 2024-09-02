package oz.yamyam_map.batch.processor;

import static oz.yamyam_map.common.code.StatusCode.*;
import static oz.yamyam_map.common.util.GeoUtils.*;

import java.util.Objects;

import org.locationtech.jts.geom.Point;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.batch.enums.RestaurantSalesType;
import oz.yamyam_map.batch.mapper.RestaurantTypeMapper;
import oz.yamyam_map.batch.util.AddressUtils;
import oz.yamyam_map.common.enums.RestaurantType;
import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.module.region.entity.Region;
import oz.yamyam_map.module.region.repository.RegionRepository;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.repository.RestaurantRepository;

@Component
@RequiredArgsConstructor
public class SeoulDataProcessor implements ItemProcessor<RowSeoulRestaurant, Restaurant> {

	private final RestaurantRepository restaurantRepository;
	private final RegionRepository regionRepository;

	@Override
	public Restaurant process(RowSeoulRestaurant item) {
		// 0. 주소 존재 여부 확인
		// 데이터 확인 시, 도로명 주소가 없다면, 지번 주소는 있고/ 지번 주소가 없다면 도로명 주소는 있었기에 해당 로직으로 처리
		String address = item.getRdnwhladdr();

		// 1. 해당 식당명과 지번 주소 혹은 도로병 주소로 값 가져오기
		Restaurant restaurant;
		if (address.isEmpty() && address.isBlank()) {
			address = item.getSitewhladdr();
			restaurant = restaurantRepository.findByNameAndOldAddressFull(item.getTrdstatenm(), address)
				.orElse(null);
		} else {
			restaurant = restaurantRepository.findByNameAndRoadAddressFull(item.getTrdstatenm(), address)
				.orElse(null);
		}

		// 2. 폐업인 경우 삭제
		if (Objects.equals(item.getDtlstatenm(), RestaurantSalesType.CLOSED.name())) {
			if (restaurant != null) {
				restaurantRepository.delete(restaurant);
			}
			return null;
		}

		// 3. 업태구분 설정
		RestaurantType restaurantType = RestaurantTypeMapper.from(item.getUptaenm());

		// 4. 시군구 매핑
		Region region = getRegionFromAddress(address);

		// 5. 위, 경도 null 인 경우 시군구 주소에서 위, 경도 가져오기
		Point point = getXYSafety(item.getX(), item.getY(), region);

		// 6. 맛집 데이터 반환
		if (restaurant == null) { // 없었던 데이터인 경우
			return Restaurant.of(region, item.getTrdstatenm(), restaurantType, item.getSitetel(),
				point, item.getSitewhladdr(),
				item.getRdnwhladdr());
		} else { // 있던 데이터의 수정인 경우
			return restaurant.updateRestaurant(region, item.getTrdstatenm(), restaurantType, item.getSitetel(),
				point, item.getSitewhladdr(),
				item.getRdnwhladdr());
		}
	}

	// 위, 경도 없는 경우 시군구로 Point 반환하는 메서드
	private Point getXYSafety(String x, String y, Region region) {
		if (x.isEmpty() || x.isBlank() || y.isEmpty() || y.isBlank()) {
			return region.getLocation();
		}

		return createPoint(Double.parseDouble(x), Double.parseDouble(y));
	}

	// 주소에서 서울 / 군구 인스턴스 가져오는 메서드
	private Region getRegionFromAddress(String item) {
		String cityDistrict = AddressUtils.extractCityDistrict(item);
		return regionRepository.findByProvinceAndCityDistrict("서울", cityDistrict)
			.orElseThrow(() -> new BusinessException(REGION_NOT_FOUND_BATCH_SERVER_ERROR));
	}
}
