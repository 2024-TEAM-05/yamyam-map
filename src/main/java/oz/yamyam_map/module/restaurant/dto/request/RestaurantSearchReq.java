package oz.yamyam_map.module.restaurant.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantSearchReq {

	@NotNull(message = "위도는 필수 파라미터입니다.")
	@Min(value = -90, message = "위도는 -90 이상이어야 합니다.")
	@Max(value = 90, message = "위도는 90 이하여야 합니다.")
	private double latitude;

	@NotNull(message = "경도는 필수 파라미터입니다.")
	@Min(value = -180, message = "경도는 -180 이상이어야 합니다.")
	@Max(value = 180, message = "경도는 180 이하여야 합니다.")
	private double longitude;

	@Min(value = 1, message = "반경은 1m 이상이어야 합니다.")
	private double range;
	
	private String sort;

	public String getSort() {
		return (sort == null || sort.isEmpty()) ? "distance" : sort;
	}

	// TODO: 페이지네이션 관련 필드 추가
}
