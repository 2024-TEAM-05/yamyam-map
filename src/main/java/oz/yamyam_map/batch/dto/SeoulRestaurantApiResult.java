package oz.yamyam_map.batch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SeoulRestaurantApiResult(
	@JsonProperty("CODE") String code,
	@JsonProperty("MESSAGE") String message
) {
}
