package oz.yamyam_map.module.restaurant.dto.response;

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
	private double pointX;
	private double pointY;
	private String oldAddressFull;
	private String roadAddressFull;
	private ReviewRating reviewRating;

	public static RestaurantDetailRes from(Restaurant restaurant) {
		return RestaurantDetailRes.builder()
			.id(restaurant.getId())
			.name(restaurant.getName())
			.restaurantType(restaurant.getRestaurantType())
			.phoneNumber(restaurant.getPhoneNumber())
			.pointX(restaurant.getLocation().getX()) // Point의 x 좌표
			.pointY(restaurant.getLocation().getY()) // Point의 y 좌표
			.oldAddressFull(restaurant.getOldAddressFull())
			.roadAddressFull(restaurant.getRoadAddressFull())
			.reviewRating(restaurant.getReviewRating())
			.build();
	}
}
