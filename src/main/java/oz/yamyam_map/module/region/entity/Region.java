package oz.yamyam_map.module.region.entity;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oz.yamyam_map.common.util.GeoUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String province;

	@Column(nullable = false)
	private String cityDistrict;

	@Column(nullable = false, columnDefinition = "Point")
	@JsonDeserialize(using = GeoUtils.PointDeserializer.class)
	private Point location;

}
