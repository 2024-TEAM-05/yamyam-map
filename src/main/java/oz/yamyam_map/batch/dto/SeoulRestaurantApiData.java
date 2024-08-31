package oz.yamyam_map.batch.dto;

import java.util.List;

import javax.xml.transform.Result;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SeoulRestaurantApiData(
	@JsonProperty("list_total_count") int totalCount,
	@JsonProperty("RESULT") SeoulRestaurantApiResult result,
	@JsonProperty("row") List<SeoulRestaurantDto> rows
) {
}
