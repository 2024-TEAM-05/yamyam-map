package oz.yamyam_map.module.region.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oz.yamyam_map.module.region.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
	Optional<Region> findByProvinceAndCityDistrict(String province, String cityDistrict);
}
