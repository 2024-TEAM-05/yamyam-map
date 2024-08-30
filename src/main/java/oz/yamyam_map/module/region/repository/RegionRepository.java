package oz.yamyam_map.module.region.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oz.yamyam_map.module.region.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}
