package oz.yamyam_map.module.region.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.module.region.dto.response.RegionResponse;
import oz.yamyam_map.module.region.service.RegionSerivce;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {

	private final RegionSerivce regionSerivce;

	// 시군구 목록 api
	@GetMapping
	public ResponseEntity<RegionResponse> getRegions() {
		RegionResponse response = regionSerivce.getRegionResponse();
		return ResponseEntity.ok(response);
	}

}
