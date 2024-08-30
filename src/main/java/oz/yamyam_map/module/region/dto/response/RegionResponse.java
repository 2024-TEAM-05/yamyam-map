package oz.yamyam_map.module.region.dto.response;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionResponse {

	@Schema(description = "시/도 리스트")
	private List<String> provinces;

	@Schema(description = "각 시/도에 해당하는 시군구 리스트")
	private Map<String, List<String>> cityDistricts;

	public static RegionResponse of(List<String> provinces, Map<String, List<String>> cityDistricts ) {
		return new RegionResponse(provinces, cityDistricts);
	}
}
