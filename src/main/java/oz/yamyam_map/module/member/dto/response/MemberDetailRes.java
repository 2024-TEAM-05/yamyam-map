package oz.yamyam_map.module.member.dto.response;

import org.locationtech.jts.geom.Point;

import lombok.Builder;
import lombok.Value;
import oz.yamyam_map.module.member.entity.Member;

@Value
@Builder
public class MemberDetailRes {

	Long id;
	String account;

	// Point 형식으로 저장된 값을 위도와 경도로 지정해 클라이언트 측에서 값 확인에 어려움이 없도록 함.
	Double latitude;
	Double longitude;

	Boolean receiveRecommendations;

	public static MemberDetailRes fromEntity(Member member) {

		Point location = member.getLocation();

		return MemberDetailRes.builder()
			.id(member.getId())
			.account(member.getAccount())
			.latitude(location != null ? location.getY() : null) // latitude
			.longitude(location != null ? location.getX() : null) // longitude
			.receiveRecommendations(member.getReceiveRecommendations())
			.build();

	}

}
