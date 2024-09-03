package oz.yamyam_map.module.restaurant.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import oz.yamyam_map.common.enums.RestaurantType;
import oz.yamyam_map.common.util.GeoUtils;
import oz.yamyam_map.module.region.entity.Region;
import oz.yamyam_map.module.restaurant.dto.request.RestaurantSearchReq;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantListRes;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.entity.ReviewRating;
import oz.yamyam_map.module.restaurant.repository.RestaurantRepository;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

	@Mock
	private RestaurantRepository restaurantRepository;

	@InjectMocks
	private RestaurantService restaurantService;

	@Test
	@DisplayName("요청에 따른 맛집 리스트 반환")
	public void shouldReturnRestaurantsWhenValidRequest() {
		// Given
		Point location1 = GeoUtils.createPoint(126.9780, 37.5665);
		Restaurant restaurant1 = Restaurant.of("얌얌식당", Region.builder().build(), RestaurantType.KOREAN_FOOD, location1,
			new ReviewRating(100L, 450L));
		List<Restaurant> mockRestaurants = List.of(restaurant1);

		RestaurantSearchReq request = RestaurantSearchReq.builder()
			.latitude(126.9780)
			.longitude(37.5665)
			.range(1000)
			.sort("distance")
			.build();

		when(restaurantRepository.findRestaurantsByLocationAndSort(
			request.getLatitude(),
			request.getLongitude(),
			request.getRange(),
			request.getSort()
			// Pageable.unpaged()
		)).thenReturn(mockRestaurants);

		// When
		RestaurantListRes result = restaurantService.getRestaurants(request);

		// Then
		assertThat(result.getRestaurants()).isNotEmpty();
		assertThat(result.getRestaurants().size()).isEqualTo(1);
		assertThat(result.getRestaurants().get(0).getName()).isEqualTo("얌얌식당");

	}
}