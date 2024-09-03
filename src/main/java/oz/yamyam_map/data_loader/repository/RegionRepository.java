package oz.yamyam_map.data_loader.repository;

import java.util.List;

import oz.yamyam_map.data_loader.RegionRecord;

public interface RegionRepository {

	void deleteAll();

	void saveAll(List<RegionRecord> data);
}
