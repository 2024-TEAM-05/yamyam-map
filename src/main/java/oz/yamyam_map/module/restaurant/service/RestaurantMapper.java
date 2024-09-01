package oz.yamyam_map.module.restaurant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import oz.yamyam_map.module.restaurant.dto.response.RestaurantSearchRes;
import oz.yamyam_map.module.restaurant.entity.Restaurant;

@Component
public class RestaurantMapper {

	public RestaurantSearchRes toRestaurantDto(Restaurant restaurant) {
		return RestaurantSearchRes.builder()
			.id(restaurant.getId())
			.name(restaurant.getName())
			.businessType(restaurant.getBusinessType())
			.location(restaurant.getLocation())
			.averageScore(restaurant.getReviewRating().getAverageScore())
			.build();
	}

	public List<RestaurantSearchRes> toRestaurantDtoList(List<Restaurant> restaurants) {
		return restaurants.stream()
			.map(this::toRestaurantDto)
			.collect(Collectors.toList());
	}

}
