package oz.yamyam_map.module.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oz.yamyam_map.module.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByAccount(String account);

	Optional<Member> findByAccount(String account);

}
