package oz.yamyam_map.module.region.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.common.BaseApiResponse;
import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.module.region.dto.response.RegionResponse;
import oz.yamyam_map.module.region.service.RegionSerivce;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController implements RegionControllerDocs {

	private final RegionSerivce regionSerivce;

	// 시군구 목록 api
	@GetMapping
	public BaseApiResponse<RegionResponse> getRegions() {
		RegionResponse response = regionSerivce.getRegions();
		return BaseApiResponse.of(StatusCode.OK, response);
	}
}
