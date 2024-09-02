package oz.yamyam_map.module.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oz.yamyam_map.module.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Optional<Restaurant> findByNameAndOldAddressFull(String name, String oldAddressFull);

	Optional<Restaurant> findByNameAndRoadAddressFull(String name, String roadAddressFull);
}
