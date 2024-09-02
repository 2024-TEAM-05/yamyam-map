package oz.yamyam_map.module.region.controller;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import oz.yamyam_map.common.code.StatusCode;
import oz.yamyam_map.common.util.GeoUtils;
import oz.yamyam_map.module.region.dto.response.RegionResponse;
import oz.yamyam_map.module.region.service.RegionSerivce;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegionController.class)
public class RegionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RegionSerivce regionSerivce;

	@Test
	@WithMockUser(username = "testUser")
	@DisplayName("GET api/region 성공")
	void shouldReturnRegions() throws Exception {
		// given
		RegionResponse.CityDistrict cityDistrict = new RegionResponse.CityDistrict(1L, "강남구",
			GeoUtils.createPoint(127.0495556, 37.514575));
		RegionResponse response = RegionResponse.of(Map.of("서울특별시", List.of(cityDistrict)));
		given(regionSerivce.getRegions()).willReturn(response);

		// when & then
		mockMvc.perform(get("/api/region"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value(StatusCode.OK.getHttpStatus().name()))
			.andExpect(jsonPath("$.data.cityDistricts.서울특별시[0].name").value("강남구"))
			.andExpect(jsonPath("$.data.cityDistricts.서울특별시[0].id").value(1L))
			.andExpect(jsonPath("$.data.cityDistricts.서울특별시[0].location.x").value(127.049556))
			.andExpect(jsonPath("$.data.cityDistricts.서울특별시[0].location.y").value(37.514575));
	}
}
