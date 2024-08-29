package oz.yamyam_map.module.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import oz.yamyam_map.module.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
