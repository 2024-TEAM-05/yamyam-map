package oz.yamyam_map.module.restaurant.repository;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import oz.yamyam_map.common.enums.RestaurantType;
import oz.yamyam_map.common.util.GeoUtils;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.entity.ReviewRating;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RestaurantRepositoryTest {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Test
	@DisplayName("반경 맛집 거리순 정렬")
	public void shouldFindRestaurantsByDistance() {
		// given
		Point location1 = GeoUtils.createPoint(126.9780, 37.5665);
		Point location2 = GeoUtils.createPoint(126.9781, 37.5666);

		Restaurant restaurant1 = Restaurant.createForTest("맛나식당", RestaurantType.KOREAN_FOOD, location1, new ReviewRating(100L, 450L));
		Restaurant restaurant2 = Restaurant.createForTest("얌얌식당", RestaurantType.WESTERN_FOOD, location2, new ReviewRating(50L, 250L));

		restaurantRepository.save(restaurant1);
		restaurantRepository.save(restaurant2);

		// when
		List<Restaurant> foundRestaurants = restaurantRepository.findRestaurantsByLocationAndSort(
			37.5665, 126.9780, 1000.0, "distance"
			// Pageable.unpaged()
		);

		// then
		assertThat(foundRestaurants).isNotEmpty();
		assertThat(foundRestaurants.size()).isEqualTo(2);
		assertThat(foundRestaurants.get(0).getName()).isEqualTo("맛나식당");
		assertThat(foundRestaurants.get(1).getName()).isEqualTo("얌얌식당");
	}

	@Test
	@DisplayName("반경 맛집 평점순 정렬")
	public void shouldFindRestaurantsByRating(){
		// given
		Point location1 = GeoUtils.createPoint(126.9780, 37.5665);
		Point location2 = GeoUtils.createPoint(126.9781, 37.5666);

		Restaurant restaurant1 = Restaurant.createForTest("맛나식당", RestaurantType.KOREAN_FOOD, location1, new ReviewRating(100L, 450L)); // 평점: 4.5
		Restaurant restaurant2 = Restaurant.createForTest("얌얌식당", RestaurantType.WESTERN_FOOD, location2, new ReviewRating(50L, 250L)); // 평점: 5.0

		restaurantRepository.save(restaurant1);
		restaurantRepository.save(restaurant2);

		// when
		List<Restaurant> foundRestaurants = restaurantRepository.findRestaurantsByLocationAndSort(
			37.5665, 126.9780, 1000.0, "rating"
			// Pageable.unpaged()
		);

		// then
		assertThat(foundRestaurants).isNotEmpty();
		assertThat(foundRestaurants.size()).isEqualTo(2);
		assertThat(foundRestaurants.get(0).getName()).isEqualTo("얌얌식당"); // 얌얌식당이 먼저 나와야 함
		assertThat(foundRestaurants.get(1).getName()).isEqualTo("맛나식당");
	}

}

