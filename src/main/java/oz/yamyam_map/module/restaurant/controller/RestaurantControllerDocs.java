package oz.yamyam_map.module.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.module.auth.security.CustomUserDetails;
import oz.yamyam_map.module.restaurant.dto.request.RestaurantSearchReq;
import oz.yamyam_map.module.restaurant.dto.request.ReviewUploadReq;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantDetailRes;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantListRes;

@Tag(name = "Restaurant", description = "맛집 관련 API")
public interface RestaurantControllerDocs {

	@Operation(summary = "리뷰 등록", description = "맛집의 리뷰를 등록합니다.")
	@ApiResponse(responseCode = "201", description = "요청이 성공했습니다.", useReturnTypeSchema = true)
	BaseApiResponse<Void> uploadReview(
		CustomUserDetails userDetails,
		Long restaurantId,
		ReviewUploadReq req
	);

	@Operation(summary = "맛집 상세 정보 조회", description = "맛집 id를 통해 상세 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청이 성공했습니다.", useReturnTypeSchema = true)
	BaseApiResponse<RestaurantDetailRes> getRestaurantDetails(Long restaurantId);

	@Operation(summary = "위, 경도 기반 맛집 리스트 정보 조회", description = "맛집 위,경도/범위/정렬방식/페이징를 통해 맛집 리스트 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청이 성공했습니다.", useReturnTypeSchema = true)
	BaseApiResponse<RestaurantListRes> getRestaurantList(RestaurantSearchReq req);
}

