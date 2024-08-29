package oz.yamyam_map.module.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import oz.yamyam_map.module.restaurant.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
