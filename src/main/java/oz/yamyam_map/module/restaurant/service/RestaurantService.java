package oz.yamyam_map.module.restaurant.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.exception.custom.BusinessException;
import oz.yamyam_map.exception.custom.DataNotFoundException;
import oz.yamyam_map.module.member.entity.Member;
import oz.yamyam_map.module.member.repository.MemberRepository;
import oz.yamyam_map.module.restaurant.dto.request.RestaurantSearchReq;
import oz.yamyam_map.module.restaurant.dto.request.ReviewUploadReq;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantDetailRes;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantListRes;
import oz.yamyam_map.module.restaurant.dto.response.RestaurantSearchRes;
import oz.yamyam_map.module.restaurant.entity.Restaurant;
import oz.yamyam_map.module.restaurant.entity.Review;
import oz.yamyam_map.module.restaurant.repository.RestaurantRepository;
import oz.yamyam_map.module.restaurant.repository.ReviewRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {
	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final RestaurantRepository restaurantRepository;
	private final RestaurantMapper restaurantMapper;

	@Transactional
	public void uploadReview(Long memberId, Long restaurantId, ReviewUploadReq req) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND));
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST));

		//리뷰 생성
		Review review = Review.of(member, restaurant, req.score(), req.content());
		reviewRepository.save(review);

		//평점 업데이트
		restaurant.uploadReview(review.getScore());
	}

	public RestaurantDetailRes getRestaurantDetails(Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new DataNotFoundException(StatusCode.RESTAURANT_NOT_FOUND));
		return RestaurantDetailRes.from(restaurant);
	}

	public RestaurantListRes getRestaurants(RestaurantSearchReq req) {
		List<Restaurant> restaurants = restaurantRepository.findRestaurantsByLocationAndSort(
			req.getLatitude(), req.getLongitude(), req.getRange(), req.getSort()
			// Pageable.unpaged()	// TODO: 페이징 처리
		);

		List<RestaurantSearchRes> restaurantResponse = restaurantMapper.toRestaurantDtoList(restaurants);
		return new RestaurantListRes(restaurantResponse);
	}
}
