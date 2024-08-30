package oz.yamyam_map.common.util;

import java.io.IOException;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

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

	/**
	 * Point 객체를 JSON으로 직렬화하는 메서드 (응답 시 간단한 x,y 형태로 보내기 위해)
	 **/
	public static class PointSerializer extends JsonSerializer<Point> {
		@Override
		public void serialize(Point value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("x", roundToSixDecimals(value.getX()));
			gen.writeNumberField("y", roundToSixDecimals(value.getY()));
			gen.writeEndObject();
		}
	}
}
