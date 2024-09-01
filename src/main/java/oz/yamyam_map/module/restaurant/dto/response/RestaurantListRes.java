package oz.yamyam_map.module.restaurant.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantListRes {
	private List<RestaurantSearchRes> restaurants;
}
