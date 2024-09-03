package oz.yamyam_map.module.restaurant.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantListRes {
	@Schema(description = "전체 맛집 수")
	private long totalElements;

	@Schema(description = "전체 페이지 수")
	private int totalPages;

	@Schema(description = "맛집 리스트")
	private List<RestaurantSearchRes> restaurants;
}
