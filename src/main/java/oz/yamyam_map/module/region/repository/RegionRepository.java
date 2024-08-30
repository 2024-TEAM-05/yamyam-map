package oz.yamyam_map.module.region.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import oz.yamyam_map.module.region.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	// 도/시 목록 중복 없이 가져옴
	@Query("SELECT DISTINCT r.province FROM region r")
	List<String> findProvinces();

	// 각 도/시의 시군구 목록 조회
	@Query("SELECT r.cityDistrict FROM region r WHERE r.province = :province")
	List<String> findCityDistrictByProvince(@Param("province") String province);
}
