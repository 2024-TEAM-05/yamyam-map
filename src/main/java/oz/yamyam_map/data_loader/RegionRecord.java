package oz.yamyam_map.data_loader;

import org.apache.commons.csv.CSVRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionRecord {
	private static final String[] HEADERS = {"do-si", "sgg", "lon", "lat"};

	private String dosi;
	private String sgg;
	private double lon;
	private double lat;

	public static String[] getHeaders() {
		return HEADERS;
	}

	public static RegionRecord from(CSVRecord record) {
		return RegionRecord.builder()
			.dosi(record.get(HEADERS[0]))
			.sgg(record.get(HEADERS[1]))
			.lon(Double.parseDouble(record.get(HEADERS[2])))
			.lat(Double.parseDouble(record.get(HEADERS[3])))
			.build();
	}
}
