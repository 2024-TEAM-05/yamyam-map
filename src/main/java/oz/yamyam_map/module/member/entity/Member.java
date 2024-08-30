package oz.yamyam_map.module.member.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oz.yamyam_map.common.entity.BaseEntity;
import oz.yamyam_map.module.member.vo.Password;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String account;

	@Embedded
	@Column(nullable = false)
	@Getter(AccessLevel.NONE)
	private Password password;

	@JdbcTypeCode(SqlTypes.GEOMETRY)
	@Column(columnDefinition = "GEOMETRY")
	private Point location;

	private Boolean receiveRecommendations;

	public static Member signUp(String account, String password, PasswordEncoder passwordEncoder) {
		return Member.builder()
			.account(account)
			.password(Password.of(password, passwordEncoder))
			.build();
	}

	public String getPassword() {
		return password.getValue();
	}

}

