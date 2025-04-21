package org.silsagusi.core.domain.article;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	private String articleCode;     // atclNo 매물번호
	private String dongCode;        // cortarNo 법정동코드
	private String articleName;     // atclNm 매물이름 (아파트/오피스텔은 단지명, 빌라는 빌라, 상가는 일반상가 등)
	private String articleType;     // rletTpNm 매물유형명 (아파트, 오피스텔 등)
	private String tradeType;       // tradTpNm 거래유형 (매매, 전세 등)
	private String floors;          // flrInfo 매물층수/건물층수
	private Integer priceSale;      // prc 매매가/보증금
	private Integer priceRent;      // rentPrc 월세
	private String areaSupply;      // spc1 공급면적 (단위: 제곱미터)
	private String areaExclusive;   // spc2 전용면적 (단위: 제곱미터)
	private String direction;       // direction 방향 (남향, 북향 등)
	private LocalDate confirmedAt;  // atclCfmYmd 건물 사용승인일
	private String imageUrl;        // repImgUrl 대표 이미지 URL
	private Double latitude;        // lat 위도
	private Double longitude;       // lng 경도
	private String articleDesc;     // articleDesc 매물 특징 설명

	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ArticleTag> tags = new ArrayList<>();  // tagList

	private String companyName;     // cpNm 정보 제공 출처
	private String agency;          // rltrNm 매물을 올린 공인중개사무소
	private Integer priceRoomMin;   // minMviFee 최소 단기임대 비용
	private Integer priceRoomMax;   // maxMviFee 최대 단기임대 비용
	private String subway;          // sbwyInfo 주변 지하철역
	private String articleDescRoom; // svcCont 고시원 기본 정보
	private String articleDescMw;   // gdrNm 고시원 특징
	private Integer emptyRoomCount; // etRoomCnt 빈방 수
	private Boolean isChecked;      // tradeCheckedByOwner 실매물 확인 여부


	private String addressFullLot;
	private String addressFullRoad;
	private String address1SiDo;
	private String address2SiGunGu;
	private String address3DongEupMyeon;
	private String addressFullLotNo1;
	private String addressFullLotNo2;
	private String addressFullRoadName;
	private String addressFullRoadNo1;
	private String addressFullRoadNo2;
	private String addressBuildingName;
	private String addressZipCode;

	public Article(
		String articleCode, String dongCode, String articleName,
		String articleType, String tradeType, String floors,
		Integer priceSale, Integer priceRent, String areaSupply,
		String areaExclusive, String direction, LocalDate confirmedAt,
		String imageUrl, Double latitude, Double longitude,
		String articleDesc, String companyName,
		String agency, Integer priceRoomMin, Integer priceRoomMax,
		String subway, String articleDescRoom, String articleDescMw,
		Integer emptyRoomCount, Boolean isChecked, Region region,
		String addressFullLot, String addressFullRoad, String address2City,
		String address2SiGunGu, String address3DongEupMyeon, String addressFullLotNo1,
		String addressFullLotNo2, String addressFullRoadName, String addressFullRoadNo1,
		String addressFullRoadNo2, String addressBuildingName, String addressZipCode
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
		this.articleDesc = articleDesc;
		this.companyName = companyName;
		this.agency = agency;
		this.priceRoomMin = priceRoomMin;
		this.priceRoomMax = priceRoomMax;
		this.subway = subway;
		this.articleDescRoom = articleDescRoom;
		this.articleDescMw = articleDescMw;
		this.emptyRoomCount = emptyRoomCount;
		this.isChecked = isChecked;
		this.region = region;
		this.addressFullLot = addressFullLot;
		this.addressFullRoad = addressFullRoad;
		this.address1SiDo = address2City;
		this.address2SiGunGu = address2SiGunGu;
		this.address3DongEupMyeon = address3DongEupMyeon;
		this.addressFullLotNo1 = addressFullLotNo1;
		this.addressFullLotNo2 = addressFullLotNo2;
		this.addressFullRoadName = addressFullRoadName;
		this.addressFullRoadNo1 = addressFullRoadNo1;
		this.addressFullRoadNo2 = addressFullRoadNo2;
		this.addressBuildingName = addressBuildingName;
		this.addressZipCode = addressZipCode;
	}

	public void addTag(Tag tag) {
		ArticleTag articleTag = new ArticleTag(this, tag);
		this.tags.add(articleTag);
	}
}
