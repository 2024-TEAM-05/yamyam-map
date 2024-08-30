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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oz.yamyam_map.common.entity.BaseEntity;
import oz.yamyam_map.common.enums.RestaurantType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Restaurant extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RestaurantType businessType;

	private String phoneNumber;

	@Column(nullable = false, columnDefinition = "GEOMETRY")
	private Point location;

	private String OldAddressFull;

	private String RoadAddressFull;

	@Embedded
	private ReviewRating reviewRating; // 리뷰 평점 데이터를 위한 VO

	public void uploadReview(Byte newRating) {
		ReviewRating newReviewRating = reviewRating.createNewReviewRating(newRating);
		this.reviewRating = newReviewRating;

	}
}
