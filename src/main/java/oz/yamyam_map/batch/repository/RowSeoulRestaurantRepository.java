package oz.yamyam_map.batch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import oz.yamyam_map.batch.domain.RowSeoulRestaurant;

public interface RowSeoulRestaurantRepository extends JpaRepository<RowSeoulRestaurant, Long> {
	List<RowSeoulRestaurant> findAllByUpdatedAt(LocalDate updatedAt);
}
