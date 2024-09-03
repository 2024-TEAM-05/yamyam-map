package oz.yamyam_map.module.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantSearchReq extends PaginationQuery {

	@Schema(description = "위도")
	@NotNull(message = "위도는 필수 파라미터입니다.")
	@Min(value = -90, message = "위도는 -90 이상이어야 합니다.")
	@Max(value = 90, message = "위도는 90 이하여야 합니다.")
	private double latitude;

	@Schema(description = "경도")
	@NotNull(message = "경도는 필수 파라미터입니다.")
	@Min(value = -180, message = "경도는 -180 이상이어야 합니다.")
	@Max(value = 180, message = "경도는 180 이하여야 합니다.")
	private double longitude;

	@Schema(description = "범위")
	@Min(value = 1, message = "반경은 1m 이상이어야 합니다.")
	private double range;

	@Schema(description = "정렬 방식", allowableValues = {"rating", "distance"}, defaultValue = "rating")
	private String sort;
}
