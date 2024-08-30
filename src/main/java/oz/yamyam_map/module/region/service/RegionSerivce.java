package oz.yamyam_map.module.region.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.module.region.dto.response.RegionResponse;
import oz.yamyam_map.module.region.entity.Region;
import oz.yamyam_map.module.region.repository.RegionRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegionSerivce {

	private final RegionRepository regionRepository;

	// 시/도와 시/도별 시군구 목록 가져오기
	public RegionResponse getRegionResponse() {
		List<Region> regions = regionRepository.findAll();

		// key:도/시, value:해당 도/시의 시군구 리스트
		Map<String, List<RegionResponse.CityDistrict>> cityRestricts = regions.stream()
			.collect(Collectors.groupingBy(
				Region::getProvince,    // 도/시로 그룹화
				Collectors.mapping(
					this::convertToCityDistrict,
					Collectors.toList()		// CityDistrict들을 List로 모으기
				)
			));

		return RegionResponse.of(cityRestricts);
	}

	private RegionResponse.CityDistrict convertToCityDistrict(Region region) {
		return new RegionResponse.CityDistrict(
			region.getId(),
			region.getCityDistrict(),
			region.getLocation()
		);
	}
}
