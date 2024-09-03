package oz.yamyam_map.batch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oz.yamyam_map.batch.dto.SeoulRestaurantDto;
import oz.yamyam_map.batch.util.HashUtils;
import oz.yamyam_map.common.entity.BaseEntity;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RowSeoulRestaurant extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String opnsfteamcode; // 개방자치단체코드

	@Column(nullable = false, unique = true)
	private String mgtno; // 관리번호

	private String apvpermymd; // 인허가일자

	private String apvcancelymd; // 인허가취소일자

	private String trdstategbn; // 영업상태코드

	private String trdstatenm; // 영업상태명

	private String dtlstategbn; // 상세영업상태코드

	private String dtlstatenm; // 상세영업상태명

	private String dcbymd; // 폐업일자

	private String clgstdt; // 휴업시작일자

	private String clgenddt; // 휴업종료일자

	private String ropnymd; // 재개업일자

	private String sitetel; // 전화번호

	private String sitearea; // 소재지면적

	private String sitepostno; // 소재지우편번호

	private String sitewhladdr; // 지번주소

	private String rdnwhladdr; // 도로명주소

	private String rdnpostno; // 도로명우편번호

	private String bplcnm; // 사업장명

	private String lastmodts; // 최종수정일자

	private String updategbn; // 데이터갱신구분

	private String updatedt; // 데이터갱신일자

	private String uptaenm; // 업태구분명

	private String x; // 좌표정보(X)

	private String y; // 좌표정보(Y)

	private String sntuptaenm; // 위생업태명

	private String maneipcnt; // 남성종사자수

	private String wmeipcnt; // 여성종사자수

	private String trdpjubnsenm; // 영업장주변구분명

	private String lvsenm; // 등급구분명

	private String wtrsplyfacilsenm; // 급수시설구분명

	private String totepnum; // 총인원

	private String hoffepcnt; // 본사종업원수

	private String fctyowkepcnt; // 공장사무직종업원수

	private String fctysiljobepcnt; // 공장판매직종업원수

	private String fctypdtjobepcnt; // 공장생산직종업원수

	private String bdngownsenm; // 건물소유구분명

	private String isream; // 보증액

	private String monam; // 월세액

	private String multusnupsoyn; // 다중이용업소여부

	private String faciltotscp; // 시설총규모

	private String jtupsoasgnno; // 전통업소지정번호

	private String jtupsomainedf; // 전통업소주된음식

	private String homepage; // 홈페이지

	private String hash; // 변경 감지를 위한 해시값

	public static RowSeoulRestaurant of(SeoulRestaurantDto dto) {
		return buildFromDto(dto);
	}

	public RowSeoulRestaurant update(SeoulRestaurantDto dto) {
		return buildFromDto(this.id, dto);
	}

	public boolean hasNotChanged(SeoulRestaurantDto newItem) {
		return hash.equals(calculateHash(newItem));
	}

	private static RowSeoulRestaurant buildFromDto(Long id, SeoulRestaurantDto dto) {
		return RowSeoulRestaurant.builder()
			.id(id)
			.opnsfteamcode(dto.opnsfteamcode())
			.mgtno(dto.mgtno())
			.apvpermymd(dto.apvpermymd())
			.apvcancelymd(dto.apvcancelymd())
			.trdstategbn(dto.trdstategbn())
			.trdstatenm(dto.trdstatenm())
			.dtlstategbn(dto.dtlstategbn())
			.dtlstatenm(dto.dtlstatenm())
			.dcbymd(dto.dcbymd())
			.clgstdt(dto.clgstdt())
			.clgenddt(dto.clgenddt())
			.ropnymd(dto.ropnymd())
			.sitetel(dto.sitetel())
			.sitearea(dto.sitearea())
			.sitepostno(dto.sitepostno())
			.sitewhladdr(dto.sitewhladdr())
			.rdnwhladdr(dto.rdnwhladdr())
			.rdnpostno(dto.rdnpostno())
			.bplcnm(dto.bplcnm())
			.lastmodts(dto.lastmodts())
			.updategbn(dto.updategbn())
			.updatedt(dto.updatedt())
			.uptaenm(dto.uptaenm())
			.x(dto.x())
			.y(dto.y())
			.sntuptaenm(dto.sntuptaenm())
			.maneipcnt(dto.maneipcnt())
			.wmeipcnt(dto.wmeipcnt())
			.trdpjubnsenm(dto.trdpjubnsenm())
			.lvsenm(dto.lvsenm())
			.wtrsplyfacilsenm(dto.wtrsplyfacilsenm())
			.totepnum(dto.totepnum())
			.hoffepcnt(dto.hoffepcnt())
			.fctyowkepcnt(dto.fctyowkepcnt())
			.fctysiljobepcnt(dto.fctysiljobepcnt())
			.fctypdtjobepcnt(dto.fctypdtjobepcnt())
			.bdngownsenm(dto.bdngownsenm())
			.isream(dto.isream())
			.monam(dto.monam())
			.multusnupsoyn(dto.multusnupsoyn())
			.faciltotscp(dto.faciltotscp())
			.jtupsoasgnno(dto.jtupsoasgnno())
			.jtupsomainedf(dto.jtupsomainedf())
			.homepage(dto.homepage())
			.hash(calculateHash(dto))
			.build();
	}

	private static RowSeoulRestaurant buildFromDto(SeoulRestaurantDto dto) {
		return buildFromDto(null, dto);
	}

	private static String calculateHash(SeoulRestaurantDto record) {
		return HashUtils.sha256FromObject(record);
	}
}
