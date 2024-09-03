package oz.yamyam_map.module.restaurant.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.module.auth.security.CustomUserDetails;
import oz.yamyam_map.module.restaurant.dto.request.RestaurantSearchReq;
import oz.yamyam_map.module.restaurant.dto.request.ReviewUploadReq;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantDetailRes;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantListRes;
import oz.yamyam_map.module.restaurant.service.RestaurantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController implements RestaurantControllerDocs {

	private final RestaurantService restaurantService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/{restaurantId}/review")
	public BaseApiResponse<Void> uploadReview(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable(name = "restaurantId") Long restaurantId,
		@RequestBody ReviewUploadReq req) {

		restaurantService.uploadReview(userDetails.getMemberId(), restaurantId, req);

		return BaseApiResponse.of(CREATED);
	}

	@ResponseStatus(OK)
	@GetMapping("/{restaurantId}")
	public BaseApiResponse<RestaurantDetailRes> getRestaurantDetails(
		@PathVariable(name = "restaurantId") Long restaurantId) {
		RestaurantDetailRes restaurantDetailRes = restaurantService.getRestaurantDetails(restaurantId);
		return BaseApiResponse.of(StatusCode.OK, restaurantDetailRes);
	}

	@ResponseStatus(OK)
	@GetMapping
	public BaseApiResponse<RestaurantListRes> getRestaurantList(
		@ModelAttribute RestaurantSearchReq req) {
		RestaurantListRes restaurantListRes = restaurantService.getRestaurants(req);
		return BaseApiResponse.of(StatusCode.OK, restaurantListRes);
	}
}
