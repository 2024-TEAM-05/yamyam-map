package oz.yamyam_map.module.region.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.module.region.dto.response.RegionResponse;

@Tag(name = "시군구", description = "시군구 조회 api")
public interface RegionControllerDocs {

	@Operation(summary = "모든 지역과 해당 시군구 목록 조회", description = "도/시와 그에 해당하는 시군구 목록을 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공했습니다."),
		@ApiResponse(responseCode = "500", description = "내부 서버 오류가 발생했습니다.")
	})
	BaseApiResponse<RegionResponse> getRegions();

}
