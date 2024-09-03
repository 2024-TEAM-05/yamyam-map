package oz.yamyam_map.module.restaurant.dto.request;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Validated
@NoArgsConstructor
public class PaginationQuery {

	@Schema(description = "페이지", defaultValue = "1")
	@Min(value = 1, message = "페이지 값은 1보다 커야합니다.")
	private int page = 1;

	@Schema(description = "페이지 당 항목 수", defaultValue = "10")
	@Min(value = 1, message = "페이지 당 항목 수는 1보다 커야합니다.")
	private int limit = 10;
}