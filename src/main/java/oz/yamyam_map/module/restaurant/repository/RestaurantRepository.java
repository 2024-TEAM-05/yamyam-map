package oz.yamyam_map.module.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import oz.yamyam_map.module.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
