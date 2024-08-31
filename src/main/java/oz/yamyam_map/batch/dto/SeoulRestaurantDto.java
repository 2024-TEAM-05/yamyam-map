package oz.yamyam_map.batch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SeoulRestaurantDto(
	@JsonProperty("OPNSFTEAMCODE") String opnsfteamcode, // 개방자치단체코드
	@JsonProperty("MGTNO") String mgtno, // 관리번호 PK
	@JsonProperty("APVPERMYMD") String apvpermymd, // 인허가일자
	@JsonProperty("APVCANCELYMD") String apvcancelymd, // 인허가취소일자
	@JsonProperty("TRDSTATEGBN") String trdstategbn, // 영업상태코드
	@JsonProperty("TRDSTATENM") String trdstatenm, // 영업상태명
	@JsonProperty("DTLSTATEGBN") String dtlstategbn, // 상세영업상태코드
	@JsonProperty("DTLSTATENM") String dtlstatenm, // 상세영업상태명
	@JsonProperty("DCBYMD") String dcbymd, // 폐업일자
	@JsonProperty("CLGSTDT") String clgstdt, // 휴업시작일자
	@JsonProperty("CLGENDDT") String clgenddt, // 휴업종료일자
	@JsonProperty("ROPNYMD") String ropnymd, // 재개업일자
	@JsonProperty("SITETEL") String sitetel, // 전화번호
	@JsonProperty("SITEAREA") String sitearea, // 소재지면적
	@JsonProperty("SITEPOSTNO") String sitepostno, // 소재지우편번호
	@JsonProperty("SITEWHLADDR") String sitewhladdr, // 지번주소
	@JsonProperty("RDNWHLADDR") String rdnwhladdr, // 도로명주소
	@JsonProperty("RDNPOSTNO") String rdnpostno, // 도로명우편번호
	@JsonProperty("BPLCNM") String bplcnm, // 사업장명
	@JsonProperty("LASTMODTS") String lastmodts, // 최종수정일자
	@JsonProperty("UPDATEGBN") String updategbn, // 데이터갱신구분
	@JsonProperty("UPDATEDT") String updatedt, // 데이터갱신일자
	@JsonProperty("UPTAENM") String uptaenm, // 업태구분명
	@JsonProperty("X") String x, // 좌표정보(X)
	@JsonProperty("Y") String y, // 좌표정보(Y)
	@JsonProperty("SNTUPTAENM") String sntuptaenm, // 위생업태명
	@JsonProperty("MANEIPCNT") String maneipcnt, // 남성종사자수
	@JsonProperty("WMEIPCNT") String wmeipcnt, // 여성종사자수
	@JsonProperty("TRDPJUBNSENM") String trdpjubnsenm, // 영업장주변구분명
	@JsonProperty("LVSENM") String lvsenm, // 등급구분명
	@JsonProperty("WTRSPLYFACILSENM") String wtrsplyfacilsenm, // 급수시설구분명
	@JsonProperty("TOTEPNUM") String totepnum, // 총인원
	@JsonProperty("HOFFEPCNT") String hoffepcnt, // 본사종업원수
	@JsonProperty("FCTYOWKEPCNT") String fctyowkepcnt, // 공장사무직종업원수
	@JsonProperty("FCTYSILJOBEPCNT") String fctysiljobepcnt, // 공장판매직종업원수
	@JsonProperty("FCTYPDTJOBEPCNT") String fctypdtjobepcnt, // 공장생산직종업원수
	@JsonProperty("BDNGOWNSENM") String bdngownsenm, // 건물소유구분명
	@JsonProperty("ISREAM") String isream, // 보증액
	@JsonProperty("MONAM") String monam, // 월세액
	@JsonProperty("MULTUSNUPSOYN") String multusnupsoyn, // 다중이용업소여부
	@JsonProperty("FACILTOTSCP") String faciltotscp, // 시설총규모
	@JsonProperty("JTUPSOASGNNO") String jtupsoasgnno, // 전통업소지정번호
	@JsonProperty("JTUPSOMAINEDF") String jtupsomainedf, // 전통업소주된음식
	@JsonProperty("HOMEPAGE") String homepage // 홈페이지
) {}
