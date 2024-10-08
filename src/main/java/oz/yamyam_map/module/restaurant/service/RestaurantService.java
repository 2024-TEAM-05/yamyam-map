package oz.yamyam_map.module.restaurant.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestaurantService {
	private static final String CACHE_PREFIX = "restaurant:";
	private static final int TTL = 24;
	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final RestaurantRepository restaurantRepository;
	private final RedisTemplate<String, Object> redisTemplate;

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
		ObjectMapper objectMapper = new ObjectMapper();
		String cacheKey = CACHE_PREFIX + restaurantId;
		Restaurant cachedRestaurant = objectMapper.convertValue(redisTemplate.opsForValue().get(cacheKey),
			new TypeReference<Restaurant>() {
			});
		if (cachedRestaurant != null) {
			log.info("{}번 맛집 데이터를 캐시에서 가져옵니다.", restaurantId);
			return RestaurantDetailRes.from(cachedRestaurant);
		}

		log.info("{}번 맛집 데이터가 캐시에 존재하지 않아 DB에서 조회합니다.", restaurantId);
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new DataNotFoundException(StatusCode.RESTAURANT_NOT_FOUND));

		if (restaurant.getReviewRating().getTotalReviews() >= 10) {
			log.info("{}번 맛집 캐시 저장 작업을 시작합니다.", restaurantId);
			redisTemplate.opsForValue().set(cacheKey, restaurant, TTL, TimeUnit.HOURS);
			log.info("{}번 맛집 캐시 저장 작업을 완료했습니다.", restaurantId);
		}

		return RestaurantDetailRes.from(restaurant);
	}

	public RestaurantListRes getRestaurants(RestaurantSearchReq req) {
		List<Restaurant> restaurants = restaurantRepository.findRestaurantsByLocationAndSort(
			req.getLatitude(), req.getLongitude(), req.getRange(), req.getSort()
			// Pageable.unpaged()	// TODO: 페이징 처리
		);

		if (restaurants.isEmpty()) {
			return RestaurantListRes.ofEmpty();
		}

		List<RestaurantSearchRes> restaurantResponse = restaurants.stream()
			.map(restaurant -> RestaurantSearchRes.builder()
				.id(restaurant.getId())
				.name(restaurant.getName())
				.restaurantType(restaurant.getRestaurantType())
				.location(restaurant.getLocation())
				.averageScore(restaurant.getReviewRating().getAverageScore())
				.build())
			.collect(Collectors.toList());

		return RestaurantListRes.of(restaurantResponse);
	}
}
