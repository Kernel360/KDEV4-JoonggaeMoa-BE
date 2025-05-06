package org.silsagusi.core.domain.article;

import java.time.LocalDate;

import org.silsagusi.core.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "articles")
@Getter
public class Article extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region_id;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "complex_id", nullable = true)
	private Complex complex_id;

	@Column(name = "article_code")
	private String articleCode;       // atclNo 매물번호
	@Column(name = "bjd_code")
	private String bjdCode;           // cortarNo 법정동코드
	@Column(name = "article_name")
	private String articleName;       // atclNm 매물이름 (아파트/오피스텔은 단지명, 빌라는 빌라, 상가는 일반상가 등)
	@Column(name = "building_type_code")
	private String buildingTypeCode;  // rletTpCd 매물유형코드 (A02 등)
	@Column(name = "building_type")
	private String buildingType;      // rletTpNm 매물유형명 (아파트, 오피스텔 등)
	@Column(name = "trade_type")
	private String tradeType;         // tradTpNm 거래유형 (매매, 전세 등)
	private String floors;            // flrInfo 매물층수/건물층수
	@Column(name = "price_sale")
	private Integer priceSale;        // prc 매매가/보증금
	@Column(name = "price_rent")
	private Integer priceRent;        // rentPrc 월세
	@Column(name = "area_supply")
	private String areaSupply;        // spc1 공급면적 (단위: 제곱미터)
	@Column(name = "area_exclusive")
	private String areaExclusive;     // spc2 전용면적 (단위: 제곱미터)
	private String direction;         // direction 방향 (남향, 북향 등)
	@Column(name = "confirmed_at")
	private LocalDate confirmedAt;    // atclCfmYmd 건물 사용승인일
	@Column(name = "image_url")
	private String imageUrl;          // repImgUrl 대표 이미지 URL
	private Double latitude;          // lat 위도
	private Double longitude;         // lng 경도
	@Column(name = "article_desc")
	private String articleDesc;       // articleDesc 매물 특징 설명
	@Column(name = "company_name")
	private String companyName;       // cpNm 정보 제공 출처
	private String agency;            // rltrNm 매물을 올린 공인중개사무소
	//	private Integer price_room_min;   // minMviFee 최소 단기임대 비용
	//	private Integer price_room_max;   // maxMviFee 최대 단기임대 비용
	private String subway;            // sbwyInfo 주변 지하철역
	//	private String description_gosiwon;  // svcCont 고시원 기본 정보
	//	private String description_gosiwon2; // gdrNm 고시원 특징
	//	private Integer count_room_empty; // etRoomCnt 빈방 수
	@Column(name = "is_checked")
	private Boolean isChecked;        // tradeCheckedByOwner 실매물 확인 여부

	@Column(name = "address_full_lot")
	private String addressFullLot;
	@Column(name = "address_full_road")
	private String addressFullRoad;
	@Column(name = "address1_si_do")
	private String address1SiDo;
	@Column(name = "address2_si_gun_gu")
	private String address2SiGunGu;
	@Column(name = "address3_dong_eup_myeon")
	private String address3DongEupMyeon;

	private Article(
		String articleCode, String bjdCode, String articleName,
		String buildingTypeCode, String buildingType, String tradeType,
		String floors, Integer priceSale, Integer priceRent,
		String areaSupply, String areaExclusive, String direction,
		LocalDate confirmedAt, String imageUrl, Double latitude,
		Double longitude, String articleDesc, String companyName,
		String agency, String subway, Boolean isChecked, Region regionId, Complex complexId
	) {
		this.articleCode = articleCode;
		this.bjdCode = bjdCode;
		this.articleName = articleName;
		this.buildingTypeCode = buildingTypeCode;
		this.buildingType = buildingType;
		this.tradeType = tradeType;
		this.floors = floors;
		this.priceSale = priceSale;
		this.priceRent = priceRent;
		this.areaSupply = areaSupply;
		this.areaExclusive = areaExclusive;
		this.direction = direction;
		this.confirmedAt = confirmedAt;
		this.imageUrl = imageUrl;
		this.latitude = latitude;
		this.longitude = longitude;
		this.articleDesc = articleDesc;
		this.companyName = companyName;
		this.agency = agency;
		this.subway = subway;
		this.isChecked = isChecked;
		this.region_id = regionId;
		this.complex_id = complexId;
	}

	public static Article create(
		String articleCode, String bjdCode, String articleName,
		String buildingTypeCode, String buildingType, String tradeType,
		String floors, Integer priceSale, Integer priceRent,
		String areaSupply, String areaExclusive, String direction,
		LocalDate confirmedAt, String imageUrl, Double latitude,
		Double longitude, String articleDesc, String companyName,
		String agency, String subway, Boolean isChecked, Region regionId, Complex complexId
	) {
		return new Article(
			articleCode, bjdCode, articleName, buildingTypeCode, buildingType, tradeType, floors, priceSale, priceRent,
			areaSupply, areaExclusive, direction, confirmedAt, imageUrl, latitude, longitude, articleDesc,
			companyName, agency, subway, isChecked, regionId, complexId
		);
	}

	public void updateAddress(String addressFullLot, String addressFullRoad, String address2City,
		String address2Sigungu, String address3Dongeupmyeon) {
		this.addressFullLot = addressFullLot;
		this.addressFullRoad = addressFullRoad;
		this.address1SiDo = address2City;
		this.address2SiGunGu = address2Sigungu;
		this.address3DongEupMyeon = address3Dongeupmyeon;
	}
}
