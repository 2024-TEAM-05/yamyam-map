package oz.yamyam_map.batch.repository;

import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;

@Repository
@RequiredArgsConstructor
public class RowSeoulRestaurantBulkRepositoryImpl implements RowSeoulRestaurantBulkRepository {
	private final EntityManager entityManager;

	@Override
	@Transactional
	public void saveAllInBatch(Chunk<? extends RowSeoulRestaurant> chunk) {
		for (RowSeoulRestaurant entity : chunk) {
			entityManager.persist(entity);
		}

		entityManager.flush();
		entityManager.clear();
	}
}
