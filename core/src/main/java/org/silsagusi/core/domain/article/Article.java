package org.silsagusi.core.domain.article;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "articles")
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	private String articleCode;     // atclNo 매물번호
	private String dongCode;       // cortarNo 법정동코드
	private String articleName;     // atclNm 매물이름 (아파트/오피스텔은 단지명, 빌라는 빌라, 상가는 일반상가 등)
	private String articleType;     // rletTpNm 매물유형명 (아파트, 오피스텔 등)
	private String tradeType;       // tradTpNm 거래유형 (매매, 전세 등)
	private String floors;          // flrInfo 매물층수/건물층수
	private Integer priceSale;      // prc 매매가/보증금
	private Integer priceRent;      // rentPrc 월세
	private Double areaSupply;      // spc1 공급면적 (단위: 제곱미터)
	private Double areaExclusive;   // spc2 전용면적 (단위: 제곱미터)
	private String direction;       // direction 방향 (남향, 북향 등)
	private LocalDate confirmedAt;  // atclCfmYmd 건물 사용승인일
	private String imageUrl;        // repImgUrl 대표 이미지 URL
	private Double latitude;        // lat 위도
	private Double longitude;       // lng 경도
	private String atclFetrDesc;    // atclFetrDesc 매물 특징 설명

	@ElementCollection
	@CollectionTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_tags_id"))
	@Column(name = "tag")
	private List<String> tags;      // tagList

	private String companyName;     // cpNm 정보 제공 출처명
	private String agentName;       // rltrNm 매물을 올린 공인중개사 사무소명
	private String subwayInfo;      // sbwyInfo 주변 지하철명
	private Boolean isChecked;      // tradeCheckedByOwner 실매물 확인 여부


	private String lotAddress;
	private String roadAddress;
	private String city;
	private String district;
	private String town;
	private String mainAddressNo;
	private String subAddressNo;
	private String roadName;
	private String mainBuildingNo;
	private String subBuildingNo;
	private String buildingName;
	private String zipCode;

	public Article(
		String articleCode, String dongCode, String articleName,
		String articleType, String tradeType, String floors,
		Integer priceSale, Integer priceRent, Double areaSupply,
		Double areaExclusive, String direction, LocalDate confirmedAt,
		String imageUrl, Double latitude, Double longitude,
		String atclFetrDesc, List<String> tags,
		String companyName, String agentName,
		String subwayInfo, Boolean isChecked, Region region,
		String lotAddress, String roadAddress, String city,
		String district, String town, String mainAddressNo,
		String subAddressNo, String roadName, String mainBuildingNo,
		String subBuildingNo, String buildingName, String zipCode
	) {
		this.articleCode = articleCode;
		this.dongCode = dongCode;
		this.articleName = articleName;
		this.articleType = articleType;
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
		this.atclFetrDesc = atclFetrDesc;
		this.tags = tags;
		this.companyName = companyName;
		this.agentName = agentName;
		this.subwayInfo = subwayInfo;
		this.isChecked = isChecked;
		this.region = region;
		this.lotAddress = lotAddress;
		this.roadAddress = roadAddress;
		this.city = city;
		this.district = district;
		this.town = town;
		this.mainAddressNo = mainAddressNo;
		this.subAddressNo = subAddressNo;
		this.roadName = roadName;
		this.mainBuildingNo = mainBuildingNo;
		this.subBuildingNo = subBuildingNo;
		this.buildingName = buildingName;
		this.zipCode = zipCode;
	}
}