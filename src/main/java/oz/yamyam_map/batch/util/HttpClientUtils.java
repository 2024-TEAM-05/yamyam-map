package oz.yamyam_map.batch.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpClientUtils {
	private static final RestClient REST_CLIENT = RestClient.create();

	/**
	 * 입력받은 URL로 HTTP GET 요청을 보내고, 응답을 입력받은 타입으로 변환합니다.
	 */
	public static <T> ResponseEntity<T> sendGetRequest(String url, Class<T> responseType) {
		try {
			return REST_CLIENT.get()
				.uri(url)
				.retrieve()
				.toEntity(responseType);
		} catch (RestClientException e) {
			throw new RestClientException("해당 URL로 HTTP GET 요청을 하는데 실패했습니다. URL: " + url, e);
		}
	}
}
