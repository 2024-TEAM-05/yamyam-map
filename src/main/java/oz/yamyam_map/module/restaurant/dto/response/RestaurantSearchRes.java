package oz.yamyam_map.module.restaurant.dto.response;

import org.locationtech.jts.geom.Point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import oz.yamyam_map.common.enums.RestaurantType;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantSearchRes {
	@Schema(description = "맛집 id")
	private Long id;

	@Schema(description = "맛집 상호명")
	private String name;

	@Schema(description = "맛집 종류")
	private RestaurantType restaurantType;

	@Schema(description = "위도, 경도")
	private Point location;

	@Schema(description = "맛집 평점")
	private double averageScore;

}
