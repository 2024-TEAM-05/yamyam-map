package oz.yamyam_map.module.restaurant.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantListRes {
	private List<RestaurantSearchRes> restaurants;
	public static RestaurantListRes of(List<RestaurantSearchRes> restaurants) {
		return new RestaurantListRes(restaurants);
	}

	public static RestaurantListRes ofEmpty() {
		return new RestaurantListRes(List.of());
	}
}
