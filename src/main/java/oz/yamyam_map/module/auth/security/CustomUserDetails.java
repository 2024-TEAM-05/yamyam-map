package oz.yamyam_map.module.auth.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.module.member.entity.Member;

@Slf4j
@Getter
public class CustomUserDetails implements UserDetails {

	private final Member member;

	public CustomUserDetails(Member member) {
		this.member = member;
	}

	@Override
	public String getUsername() {
		return member.getAccount();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		log.info("CustomUserDetails.getPassword() 호출 - 반환된 비밀번호: {}", member.getPassword());
		return member.getPassword();
	}

	public Long getMemberId() {
		return member.getId();
	}

	public String getAccount() {
		return member.getAccount();
	}

}
