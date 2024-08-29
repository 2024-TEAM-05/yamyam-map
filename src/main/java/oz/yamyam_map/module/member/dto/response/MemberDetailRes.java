package oz.yamyam_map.module.member.dto.response;

import lombok.Builder;
import lombok.Value;
import oz.yamyam_map.module.member.entity.Member;

@Value
@Builder
public class MemberDetailRes {

	Long id;
	String account;

	Double latitude;
	Double longitude;
	Boolean receiveRecommendations;

	public static MemberDetailRes fromEntity(Member member) {

		return MemberDetailRes.builder()
			.id(member.getId())
			.account(member.getAccount())
			.latitude(member.getLatitude())
			.longitude(member.getLongitude())
			.receiveRecommendations(member.getReceiveRecommendations())
			.build();

	}
}
