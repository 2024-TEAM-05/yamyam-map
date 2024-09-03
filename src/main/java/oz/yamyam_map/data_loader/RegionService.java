package oz.yamyam_map.data_loader;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.data_loader.repository.RegionRepository;
import oz.yamyam_map.data_loader.utils.CsvUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RegionService {
	private final String FILE_PATH = "src/main/resources/sgg_lat_lon.csv";
	private final RegionRepository regionRepository;

	@Transactional
	public void load(String filePath) throws IOException {
		List<RegionRecord> regions = CsvUtils.read(filePath, RegionRecord.getHeaders())
			.stream()
			.map(RegionRecord::from)
			.toList();

		regionRepository.deleteAll();
		regionRepository.saveAll(regions);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			load(FILE_PATH);
		} catch (Exception e) {
			log.error("시군구 데이터 업로드를 실패했습니다.", e);
		}
	}
}
