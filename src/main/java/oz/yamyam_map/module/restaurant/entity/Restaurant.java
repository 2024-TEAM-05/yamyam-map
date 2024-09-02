package oz.yamyam_map.module.restaurant.entity;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oz.yamyam_map.common.entity.BaseEntity;
import oz.yamyam_map.common.enums.RestaurantType;
import oz.yamyam_map.module.region.entity.Region;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Restaurant extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RestaurantType restaurantType;

	private String phoneNumber;

	@Column(nullable = false, columnDefinition = "GEOMETRY")
	private Point location;

	private String oldAddressFull;

	private String roadAddressFull;

	@Embedded
	private ReviewRating reviewRating; // 리뷰 평점 데이터를 위한 VO

	public Restaurant updateRestaurant(Region region, String name, RestaurantType restaurantType, String phoneNumber,
		Point location, String oldAddressFull, String roadAddressFull){
		this.region = region;
		this.name = name;
		this.restaurantType = restaurantType;
		this.phoneNumber = phoneNumber;
		this.location = location;
		this.oldAddressFull = oldAddressFull;
		this.roadAddressFull = roadAddressFull;
		return this;
	}

	public static Restaurant of(Region region, String name, RestaurantType restaurantType, String phoneNumber,
		Point location, String oldAddressFull, String roadAddressFull) {
		return Restaurant.builder()
			.region(region)
			.name(name)
			.restaurantType(restaurantType)
			.phoneNumber(phoneNumber)
			.location(location)
			.oldAddressFull(oldAddressFull)
			.roadAddressFull(roadAddressFull)
			.reviewRating(new ReviewRating())
			.build();
	}

	public void uploadReview(Byte newRating) {
		ReviewRating newReviewRating = reviewRating.createNewReviewRating(newRating);
		this.reviewRating = newReviewRating;

	}
}
