package oz.yamyam_map.module.region.dto.response;

import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionResponse {

	@Schema(description = "시/도 별로 그룹화 한 시군구 리스트")
	private Map<String, List<CityDistrict>> cityDistricts;

	public static RegionResponse of(Map<String, List<CityDistrict>> cityDistricts) {
		return new RegionResponse(cityDistricts);
	}

	// 시군구 정보(id, 위치) 같이 뿌려주기 위한 CityDistrict InnerClass
	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class CityDistrict {
		private Long id;
		private String name;
		private Point location;
	}
}
