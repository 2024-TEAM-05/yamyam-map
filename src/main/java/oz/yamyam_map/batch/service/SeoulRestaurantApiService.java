package oz.yamyam_map.batch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import oz.yamyam_map.batch.config.properties.SeoulDataPipelineProperties;
import oz.yamyam_map.batch.dto.SeoulRestaurantApiData;
import oz.yamyam_map.batch.dto.SeoulRestaurantApiResponse;
import oz.yamyam_map.batch.dto.SeoulRestaurantApiResult;
import oz.yamyam_map.batch.dto.SeoulRestaurantDto;
import oz.yamyam_map.batch.util.HttpClientUtils;

@Component
public class SeoulRestaurantApiService {
	private static final String CODE_EMPTY = "INFO-200";
	private static final String CODE_OK = "INFO-000";
	private final String baseUrl;

	public SeoulRestaurantApiService(SeoulDataPipelineProperties properties) {
		this.baseUrl = buildBaseURL(properties);
	}

	public List<SeoulRestaurantDto> getRestaurants(int offset, int limit) {
		// HTTP 요청 URL 생성
		String url = buildUrlWithRange(offset, limit);

		// HTTP 요청 (GET)
		ResponseEntity<SeoulRestaurantApiResponse> response = HttpClientUtils.sendGetRequest(url,
			SeoulRestaurantApiResponse.class);

		// 응답에서 데이터 가져오기
		return extractData(response);
	}

	private String buildBaseURL(SeoulDataPipelineProperties properties) {
		return String.format("%s/%s/%s/%s/",
			properties.host(),
			properties.authKey(),
			properties.responseFormat(),
			properties.serviceName());
	}

	private String buildUrlWithRange(int offset, int limit) {
		int end = calculateEnd(offset, limit);
		return String.format("%s/%d/%d/", baseUrl, offset, end);
	}

	/**
	 * offset과 limit을 이용하여 end 값을 계산
	 */
	private static int calculateEnd(int offset, int limit) {
		return offset + limit - 1;
	}

	private List<SeoulRestaurantDto> extractData(ResponseEntity<SeoulRestaurantApiResponse> response) {
		// BODY 정보 가져오기 (200 응답이 아닌 경우 예외를 던짐)
		SeoulRestaurantApiResponse body = Optional.of(response)
			.filter(entity -> entity.getStatusCode() == HttpStatus.OK)
			.map(ResponseEntity::getBody)
			.orElseThrow(() -> new RestClientException("요청이 정상 처리되지 않았습니다. 상태코드 : " + response.getStatusCode()));

		// API 결과 데이터 가져오기 (데이터가 Null인 경우 예외를 던짐)
		SeoulRestaurantApiResult result = Optional.ofNullable(body.data())
			.map(SeoulRestaurantApiData::result)
			.orElseThrow(() -> new RestClientException("API 응답 데이터가 올바르지 않습니다."));

		// API 결과 코드가 INFO-200인 경우, 빈 리스트 반환
		if (CODE_EMPTY.equals(result.code())) {
			return List.of();
		}

		// API 결과 코드가 INFO-000이 아닌 경우, 정상 처리가 아니기 떄문에 에러 반환
		if (!CODE_OK.equals(result.code())) {
			throw new RestClientException("API error: " + result.code() + " " + result.message());
		}

		return body.data().rows();
	}

}
