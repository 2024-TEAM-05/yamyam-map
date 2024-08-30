package oz.yamyam_map.common.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
public class GeoUtils {

	private static final int SRID_WGS84 = 4326;
	private static final double SCALING_FACTOR = 1_000_000.0;

	/**
	 * 위, 경도 저장을 위한 Point 생성하는 메서드
	 * 경도 > 위도 순으로 저장
	 **/
	public static Point createPoint(double longitude, double latitude) {
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID_WGS84);
		Coordinate coordinate = new Coordinate(longitude, latitude);
		Point point = geometryFactory.createPoint(coordinate);
		point.setSRID(SRID_WGS84);
		return point;
	}


	/**
	 * 위, 경도 소수점 6자리만 들어가도록 설정하는 메서드
	 **/
	private static double roundToSixDecimals(double value) {
		return Math.round(value * SCALING_FACTOR) / SCALING_FACTOR;
	}
}
