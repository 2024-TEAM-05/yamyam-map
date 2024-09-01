package oz.yamyam_map.module.restaurant.dto.response;

import java.util.List;

import org.locationtech.jts.geom.Point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import oz.yamyam_map.common.enums.RestaurantType;

@Getter
@AllArgsConstructor
public class RestaurantSearchRes {
	private Long id;
	private String name;
	private RestaurantType businessType;
	private Point location;
	private double averageScore;

}
