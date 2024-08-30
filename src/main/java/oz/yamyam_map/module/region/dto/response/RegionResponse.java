package oz.yamyam_map.module.region.dto.response;

import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import oz.yamyam_map.common.util.GeoUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "시군구 조회 API의 응답 객체")
public class RegionResponse {

	@Schema(description = "시/도 별로 그룹화 한 시군구 리스트")
	private Map<String, List<CityDistrict>> cityDistricts;

	public static RegionResponse of(Map<String, List<CityDistrict>> cityDistricts) {
		return new RegionResponse(cityDistricts);
	}

	// 시군구 정보(id, 위치) 같이 뿌려주기 위한 CityDistrict InnerClass
	@Getter
	@AllArgsConstructor
	@Schema(description = "시군구 데이터")
	public static class CityDistrict {
		private Long id;
		private String name;

		@Schema(description = "Location in x, y 좌표", example = "{\"x\": 127.028, \"y\": 37.496}")
		@JsonSerialize(using = GeoUtils.PointSerializer.class)
		private Point location;
	}
}
