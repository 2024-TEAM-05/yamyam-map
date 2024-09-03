package oz.yamyam_map.batch.repository;

import org.springframework.batch.item.Chunk;

import oz.yamyam_map.batch.domain.RowSeoulRestaurant;

public interface RowSeoulRestaurantBulkRepository {
	void saveAllInBatch(Chunk<? extends RowSeoulRestaurant> chunk);
}
