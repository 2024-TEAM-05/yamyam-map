package oz.yamyam_map.module.restaurant.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewUploadReq(

	@NotNull(message = "점수는 꼭 필요합니다.")
	@Min(value = 0, message = "평가 점수는 0 이상이어야 합니다.")
	@Max(value = 5, message = "평가 점수는 5 이하여야 합니다.")
	Byte score,

	@Size(max = 255, message = "리뷰 내용은 255글자 이하여야 합니다.")
	String content
) {

}
