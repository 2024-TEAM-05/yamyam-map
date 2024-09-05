package oz.yamyam_map.batch.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oz.yamyam_map.batch.domain.RowSeoulRestaurant;

public interface RowSeoulRestaurantRepository
	extends JpaRepository<RowSeoulRestaurant, Long>, RowSeoulRestaurantBulkRepository {
	List<RowSeoulRestaurant> findAllByUpdatedAt(LocalDate updatedAt);

	Optional<RowSeoulRestaurant> findByMgtno(String mgtno);
}
