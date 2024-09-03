package oz.yamyam_map.batch.processor;

import static oz.yamyam_map.common.util.GeoUtils.*;

import java.util.Objects;

import org.locationtech.jts.geom.Point;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.batch.enums.RestaurantSalesType;
import oz.yamyam_map.batch.mapper.RestaurantTypeMapper;
import oz.yamyam_map.batch.util.AddressUtils;
import oz.yamyam_map.common.enums.RestaurantType;
import oz.yamyam_map.module.region.entity.Region;
import oz.yamyam_map.module.region.repository.RegionRepository;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.repository.RestaurantRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeoulDataProcessor implements ItemProcessor<RowSeoulRestaurant, Restaurant> {

	public static final String SEOUL = "서울";

	private final RestaurantRepository restaurantRepository;
	private final RegionRepository regionRepository;

	@Override
	public Restaurant process(RowSeoulRestaurant item) {
		// 0. 유효한 주소 가져오기
		String address = getValidAddress(item);

		// 1. 해당 식당명과 지번 주소 혹은 도로병 주소로 값 가져오기
		Restaurant restaurant = findExistingRestaurant(item.getTrdstatenm(), address);

		// 2. 폐업인 경우 삭제
		if (isRestaurantClosed(item, restaurant)) {
			return null;
		}

		// 3. 업태구분 설정
		RestaurantType restaurantType = RestaurantTypeMapper.from(item.getUptaenm());

		// 4. 시군구 매핑
		Region region = getRegionByAddress(address);
		if (region == null) {
			log.error("SeoulDataProcessor - 해당 시군구를 Region 엔티티에서 찾을 수 없습니다.[ 관리번호: {}, 주소: {} ]", item.getMgtno(),
				address);
			return null;
		}

		// 5. 위, 경도 null 인 경우 시군구 주소에서 위, 경도 가져오기
		Point location = getXYSafety(item.getX(), item.getY(), region);

		// 6. 맛집 데이터 반환
		return saveOrUpdateRestaurant(restaurant, region, item, restaurantType, location);
	}

	// 지번/ 도로명 중 있는 주소 가져오기
	private String getValidAddress(RowSeoulRestaurant item) {
		return StringUtils.isNotBlank(item.getRdnwhladdr()) ? item.getRdnwhladdr() : item.getSitewhladdr();
	}

	// 레스토랑이 존재하는지 확인하는 메서드
	private Restaurant findExistingRestaurant(String name, String address) {
		if (StringUtils.isBlank(address)) {
			return null;
		}
		return restaurantRepository.findByNameAndOldAddressFull(name, address)
			.orElseGet(() -> restaurantRepository.findByNameAndRoadAddressFull(name, address).orElse(null));
	}

	// 폐업 여부 확인하는 메서드
	private boolean isRestaurantClosed(RowSeoulRestaurant item, Restaurant restaurant) {
		if (Objects.equals(item.getDtlstatenm(), RestaurantSalesType.CLOSED.name())) {
			if (restaurant != null) {
				restaurantRepository.delete(restaurant);
			}
			return true;
		}
		return false;
	}

	// 주소에서 서울 / 군구 인스턴스 가져오는 메서드
	private Region getRegionByAddress(String item) {
		String cityDistrict = AddressUtils.extractCityDistrict(item);
		return regionRepository.findByProvinceAndCityDistrict(SEOUL, cityDistrict)
			.orElse(null);
	}

	// 위, 경도 없는 경우 시군구로 Point 반환하는 메서드
	private Point getXYSafety(String x, String y, Region region) {
		if (x.isEmpty() || x.isBlank() || y.isEmpty() || y.isBlank()) {
			return region.getLocation();
		}

		return createPoint(Double.parseDouble(x), Double.parseDouble(y));
	}

	// 수정, 생성 여부에 따라 맛집 저장하는 플로우
	private Restaurant saveOrUpdateRestaurant(Restaurant restaurant, Region region, RowSeoulRestaurant item,
		RestaurantType restaurantType, Point location) {
		if (restaurant == null) {
			return Restaurant.of(region, item.getTrdstatenm(), restaurantType, item.getSitetel(),
				location, item.getSitewhladdr(), item.getRdnwhladdr());
		} else {
			return restaurant.updateRestaurant(region, item.getTrdstatenm(), restaurantType, item.getSitetel(),
				location, item.getSitewhladdr(), item.getRdnwhladdr());
		}
	}

}
