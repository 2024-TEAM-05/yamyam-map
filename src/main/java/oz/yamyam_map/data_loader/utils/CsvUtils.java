package oz.yamyam_map.data_loader.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public final class CsvUtils {

	// CSV 파일명과 파일 헤더 정보를 통해 CSVRecord 리스트를 반환하는 메서드
	public static List<CSVRecord> read(String filePath, String[] headers) throws IOException {
		CSVFormat csvFormat = buildCSVFormat(headers);
		Reader io = new FileReader(filePath);

		return csvFormat.parse(io)
			.stream()
			.toList();
	}

	private static CSVFormat buildCSVFormat(String[] headers) {
		return CSVFormat.DEFAULT.builder()
			.setHeader(headers)
			.setSkipHeaderRecord(true)
			.build();
	}
}
