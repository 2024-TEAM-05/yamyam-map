package oz.yamyam_map.module.restaurant.dto.response;

import org.locationtech.jts.geom.Point;

import lombok.Builder;
import lombok.Getter;
import oz.yamyam_map.common.enums.RestaurantType;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.entity.ReviewRating;

@Builder
@Getter
public class RestaurantDetailRes {
	private Long id;
	private String name;
	private RestaurantType restaurantType;
	private String phoneNumber;
	private Point location;
	private String oldAddressFull;
	private String roadAddressFull;
	private ReviewRating reviewRating;

	public static RestaurantDetailRes from(Restaurant restaurant) {
		return RestaurantDetailRes.builder()
			.id(restaurant.getId())
			.name(restaurant.getName())
			.restaurantType(restaurant.getRestaurantType())
			.phoneNumber(restaurant.getPhoneNumber())
			.location(restaurant.getLocation())
			.oldAddressFull(restaurant.getOldAddressFull())
			.roadAddressFull(restaurant.getRoadAddressFull())
			.reviewRating(restaurant.getReviewRating())
			.build();
	}
}
