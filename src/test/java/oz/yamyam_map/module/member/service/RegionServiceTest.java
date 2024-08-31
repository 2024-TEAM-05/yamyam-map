package oz.yamyam_map.module.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import oz.yamyam_map.common.util.GeoUtils;
import oz.yamyam_map.module.region.dto.response.RegionResponse;
import oz.yamyam_map.module.region.entity.Region;
import oz.yamyam_map.module.region.repository.RegionRepository;
import oz.yamyam_map.module.region.service.RegionSerivce;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {

	@Mock
	private RegionRepository regionRepository;

	@InjectMocks
	private RegionSerivce regionSerivce;

	private Region region;

	@BeforeEach
	void setUp() {
		region = Region.builder()
			.id(1L)
			.province("서울특별시")
			.cityDistrict("강남구")
			.location(GeoUtils.createPoint(127.0495556, 37.514575))
			.build();
	}

	@Test
	@DisplayName("시군구 목록 반환")
	void shouldReturnRegionList() {
		// given
		given(regionRepository.findAll()).willReturn(List.of(region));

		// when
		RegionResponse response = regionSerivce.getRegions();

		// then
		assertNotNull(response);
		assertEquals(1, response.getCityDistricts().size());

		RegionResponse.CityDistrict expectedCityDistrict = new RegionResponse.CityDistrict(
			1L, "강남구", GeoUtils.createPoint(127.0495556, 37.514575)
		);
		RegionResponse.CityDistrict actualCityDistrict = response.getCityDistricts().get("서울특별시").get(0);
		assertEquals(expectedCityDistrict, actualCityDistrict);
	}

	@Test
	@DisplayName("목록 없을 시 빈 목록 반환")
	void shouldReturnEmptyMapWhenNoData() {
		// given
		given(regionRepository.findAll()).willReturn(Collections.emptyList());

		// when
		RegionResponse response = regionSerivce.getRegions();

		// then
		assertNotNull(response);
		assertEquals(0, response.getCityDistricts().size());
	}
}
