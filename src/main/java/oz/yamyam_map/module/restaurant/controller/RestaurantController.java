package oz.yamyam_map.module.restaurant.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.module.restaurant.RestaurantService;
import oz.yamyam_map.module.restaurant.dto.request.ReviewUploadReq;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantDetailRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController implements RestaurantControllerDocs {
	private final RestaurantService restaurantService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/{restaurantId}/review")
	public BaseApiResponse<Void> uploadReview(@PathVariable(name = "restaurantId") Long restaurantId,
		@RequestBody ReviewUploadReq req) {

		// TODO : 커스텀 어노테이션 생성 후 삭제
		Long tmpMemberId = 1L;

		restaurantService.uploadReview(tmpMemberId, restaurantId, req);

		return BaseApiResponse.of(CREATED);
	}

	@ResponseStatus(OK)
	@GetMapping("/{restaurantId}")
	public BaseApiResponse<List<RestaurantDetailRes>> getRestaurantDetails(
		@PathVariable(name = "restaurantId") Long restaurantId) {
		List<RestaurantDetailRes> restaurantDetailRes = restaurantService.getRestaurantDetails(restaurantId);
		return BaseApiResponse.of(OK, restaurantDetailRes);
	}
}
