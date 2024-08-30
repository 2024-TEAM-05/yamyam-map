package oz.yamyam_map.module.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateReq {

	@Schema(description = "위도", example = "37.514575")
	@Min(value = -90, message = "위도 값은 -90도 이상이어야 합니다.")
	@Max(value = 90, message = "위도 값은 90도 이하여야 합니다.")
	private Double latitude;

	@Schema(description = "경도", example = "127.0495556")
	@Min(value = -180, message = "경도 값은 -180도 이상이어야 합니다.")
	@Max(value = 180, message = "경도 값은 180도 이하여야 합니다.")
	private Double longitude;

	@Schema(description = "점심 추천 알림 활성화 유무", example = "true")
	private Boolean receiveRecommendations;

}