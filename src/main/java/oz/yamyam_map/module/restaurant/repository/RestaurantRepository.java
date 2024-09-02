package oz.yamyam_map.module.restaurant.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oz.yamyam_map.module.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	/**
	 * Hibernate Spatial
	 * - ST_DWithin: 좌표와 맛집 위치 간의 거리가 특정 반경 내에 있는지 계산하여 확인
	 * - ST_Distance: 맛집과 좌표 간의 거리를 계산
	 */
	@Query("SELECT r FROM Restaurant r " +
		"WHERE ST_Distance(r.location, ST_GeomFromText(CONCAT('POINT(', :latitude, ' ', :longitude, ')'), 4326)) <= :range " +
		"ORDER BY " +
		"CASE WHEN :sort = 'rating' THEN r.reviewRating.averageScore END DESC, " +
		"CASE WHEN :sort = 'distance' THEN ST_Distance(r.location, ST_GeomFromText(CONCAT('POINT(', :latitude, ' ', :longitude, ')'), 4326)) END ASC")
	List<Restaurant> findRestaurantsByLocationAndSort(
		@Param("latitude") double latitude,
		@Param("longitude") double longitude,
		@Param("range") double range,
		@Param("sort") String sort
	);
}
