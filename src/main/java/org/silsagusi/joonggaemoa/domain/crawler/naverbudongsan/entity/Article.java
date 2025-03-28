package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity;


import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity(name = "articles")
public class Article {
    /** 매물 ID (JSON 키: articleNo) */
    @Id
    private Long articleId;

    /** 지역 코드 (JSON 키: cortarNo) - 행정동 코드 등 지리적 위치 식별자 */
    private String regionCode;

    /** 매물 이름 또는 단지명 (JSON 키: articleName) */
    private String articleName;

    /** 매물 상태 코드 (JSON 키: atclStatCd) - 예: R0(정상) 등 */
    private String statusCode;

    /** 부동산 유형 코드 (JSON 키: realEstateTypeCode) - 예: A01(아파트), A02(오피스텔) 등 */
    private String realEstateTypeCode;

    /** 부동산 유형 이름 (JSON 키: realEstateTypeName) - 예: "아파트", "오피스텔" 등 */
    private String realEstateTypeName;

    /** 거래 유형 코드 (JSON 키: tradeTypeCode) - 예: A1(매매), B1(전세), B2(월세) */
    private String tradeTypeCode;

    /** 거래 유형 이름 (JSON 키: tradeTypeName) - 예: "매매", "전세", "월세" 등 */
    private String tradeTypeName;

    /** 매물 검증 유형 코드 (JSON 키: verificationTypeCode) - 예: SITE(사이트 확인), OWNER(직접 등록) 등 */
    private String verificationTypeCode;

    /** 층 정보 (JSON 키: floorInfo) - 예: "고/10", "7/15" 등 (현재층/전체층 또는 층 구분) */
    private String floorInfo;

    /** 매매가 또는 보증금 (JSON 키: dealOrWarrantPrc) - 거래 금액 (만원 단위) */
    private Long dealOrWarrantPrice;

    /** 월세 금액 (JSON 키: rentPrice) - 월 임대료 (만원 단위, 전세나 매매의 경우 0 또는 해당 없음) */
    private Long rentPrice;

    /** 공급 면적 (JSON 키: area1) - 공급면적(㎡), 실수치 */
    private Double supplyArea;

    /** 전용 면적 (JSON 키: area2) - 전용면적(㎡), 실수치 */
    private Double exclusiveArea;

    /** 면적명/타입 (JSON 키: areaName) - 평형이나 타입 이름 (예: "84A") */
    private String areaName;

    /** 방향 (JSON 키: direction) - 예: "남향", "동향" 등 주향 방향 */
    private String direction;

    /** 매물 확인일자 (JSON 키: articleConfirmYmd) - 매물 등록/확인 날짜 */
    private LocalDate articleConfirmDate;

    /** 매물 특징 설명 (JSON 키: articleFeatureDesc) */
    private String articleFeatureDescription;

    /** 매물 태그 목록 (JSON 키: tagList) */
    @ElementCollection
    @CollectionTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "tag")
    private List<String> tagList;

    /** 동(건물) 이름 (JSON 키: buildingName) */
    private String buildingName;

    /** 기준 지점까지 거리/시간 (JSON 키: minute) - 예: 검색 기준지까지 도보 시간 (분) */
    private Integer minute;

    /** 동일 주소 매물 수 (JSON 키: sameAddrCnt) - 같은 주소(단지/건물)에 있는 매물 개수 */
    private Integer sameAddressCount;

    /** 동일 주소 직거래 매물 수 (JSON 키: sameAddrDirectCnt) - 동일 주소 매물 중 직거래인 매물 개수 */
    private Integer sameAddressDirectCount;

    /** 동일 주소 매물 그룹 해시 (JSON 키: sameAddrHash) - 동일 주소를 식별하는 해시값 */
    private String sameAddressHash;

    /** 동일 주소 매물 중 최고가 (JSON 키: sameAddrMaxPrc) - 같은 주소 매물들의 최고 금액 (예: "9억 6,000") */
    private String sameAddressMaxPrice;

    /** 동일 주소 매물 중 최저가 (JSON 키: sameAddrMinPrc) - 같은 주소 매물들의 최저 금액 (예: "9억") */
    private String sameAddressMinPrice;

    /** 콘텐츠 제공자 ID (JSON 키: cpId) - 매물 정보 제공 주체 ID (예: "NEONET", "bizmk" 등) */
    private String cpId;

    /** 콘텐츠 제공자 이름 (JSON 키: cpName) - 매물 정보 제공 주체 명칭 (예: "부동산뱅크", "매경부동산") */
    private String cpName;

    /** 매물 제공 콘텐츠 수 (JSON 키: cpCount) - 해당 매물을 제공하는 중복 매체 수 */
    private Integer cpCount;

    /** 중개사 이름 (JSON 키: realtorName) */
    private String realtorName;

    /** 직거래 여부 (JSON 키: directTradYn) - 직거래 매물일 경우 true (Y), 아니면 false (N) */
    private Boolean directTrade;

    /** 최소 MVI 비용 (JSON 키: minMviFee) - (의미 불상, JSON 데이터 필드 유지) */
    private Integer minMoveInFee;

    /** 최대 MVI 비용 (JSON 키: maxMviFee) - (의미 불상, JSON 데이터 필드 유지) */
    private Integer maxMoveInFee;

    /** 별도 추가 방 개수 (JSON 키: etRoomCnt) - 예: 알파룸 등 추가 공간 개수 */
    private Integer extraRoomCount;

    /** 대표 이미지 URL (JSON 키: representativeImgUrl) */
    private String representativeImgUrl;

    /** 대표 이미지 타입 코드 (JSON 키: repImgTpCd) */
    private String representativeImgTypeCode;

    /** 대표 이미지 썸네일 규격 (JSON 키: repImgThumb) */
    private String representativeImgThumb;

    /** 위도 (JSON 키: lat) */
    private Double latitude;

    /** 경도 (JSON 키: lng) */
    private Double longitude;

    /** 원본 매물 PC 링크 (JSON 키: cpPcArticleUrl) */
    private String cpPcArticleUrl;

    // (cpLinkVO 필드는 JSON 응답에 포함되었지만 사용되지 않으므로 제외함)
    // ...기타 필요한 메소드들(생성자, Getter/Setter 등)...
}