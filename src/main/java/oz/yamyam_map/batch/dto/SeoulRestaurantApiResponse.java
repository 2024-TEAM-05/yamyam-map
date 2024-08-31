package oz.yamyam_map.batch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SeoulRestaurantApiResponse(
	@JsonProperty("LOCALDATA_072404")
	SeoulRestaurantApiData data
) {
}
