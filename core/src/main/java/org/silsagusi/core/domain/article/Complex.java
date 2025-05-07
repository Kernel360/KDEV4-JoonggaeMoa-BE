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
@Table(name = "complexes")
@Getter
public class Complex extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complex_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	@Column(name = "complex_code")
	private String complexCode; // 단지 고유 번호
	@Column(name = "complex_name")
	private String complexName; // 단지 이름
	@Column(name = "count_dong")
	private Integer countDong; // 총 동 수
	@Column(name = "count_household")
	private Integer countHousehold; // 총 세대 수
	//@Column(name = "count_household_general")
	//private Integer countHouseholdGeneral; // 일반 세대 수 (일반적으로 0으로 표시됨)
	@Column(name = "confirmed_at")
	private LocalDate confirmedAt; // 사용 승인일 (예: “2025.02.”)
	@Column(name = "count_deal")
	private Integer countDeal; // 매매 건수
	@Column(name = "count_lease")
	private Integer countLease; // 전세 건수
	@Column(name = "count_rent")
	private Integer countRent; // 월세 건수
	@Column(name = "count_rent_short_term")
	private Integer countRentShortTerm; // 단기 임대 건수
	@Column(name = "count_articles")
	private Integer countArticles; // 총 매물 수
	@Column(name = "size_min")
	private String sizeMin; // 최소 전용면적 (㎡)
	@Column(name = "size_max")
	private String sizeMax; // 최대 전용면적 (㎡)
	@Column(name = "price_sale_initial_min")
	private Integer priceSaleInitialMin; // 최소 분양가 (일반적으로 0으로 표시됨)
	@Column(name = "price_sale_initial_max")
	private Integer priceSaleInitialMax; // 최대 분양가 (일반적으로 0으로 표시됨)
	@Column(name = "tour_exists")
	private Boolean tourExists; // 투어 가능 여부
	@Column(name = "is_seismic")
	private Boolean isSeismic; // 내진설계여부
	@Column(name = "count_elevator")
	private Integer countElevator; // 총 엘리베이터 수

	private Double latitude;
	private Double longitude;

	private Complex(
		String complexCode, String complexName,
		Integer countDong, Integer countHousehold, LocalDate confirmedAt,
		Integer countDeal, Integer countLease, Integer countRent,
		Integer countRentShortTerm, Integer countArticles,
		String sizeMin, String sizeMax, Integer priceSaleInitialMin,
		Integer priceSaleInitialMax, Boolean tourExists,
		Boolean isSeismic, Integer countElevator, Double latitude, Double longitude, Region region
	) {
		this.complexCode = complexCode;
		this.complexName = complexName;
		this.countDong = countDong;
		this.countHousehold = countHousehold;
		this.confirmedAt = confirmedAt;
		this.countDeal = countDeal;
		this.countLease = countLease;
		this.countRent = countRent;
		this.countRentShortTerm = countRentShortTerm;
		this.countArticles = countArticles;
		this.sizeMin = sizeMin;
		this.sizeMax = sizeMax;
		this.priceSaleInitialMin = priceSaleInitialMin;
		this.priceSaleInitialMax = priceSaleInitialMax;
		this.tourExists = tourExists;
		this.isSeismic = isSeismic;
		this.countElevator = countElevator;
		this.latitude = latitude;
		this.longitude = longitude;
		this.region = region;
	}

	public static Complex create(
		String complexCode, String complexName, Integer countDong,
		Integer countHousehold, LocalDate confirmedAt, Integer countDeal,
		Integer countLease, Integer countRent, Integer countRentShortTerm,
		Integer countArticles, String sizeMin, String sizeMax,
		Integer priceSaleInitialMin, Integer priceSaleInitialMax,
		Boolean tourExists, Boolean isSeismic,
		Integer countElevator, Double latitude, Double longitude, Region region
	) {
		return new Complex(
			complexCode, complexName, countDong,
			countHousehold, confirmedAt, countDeal,
			countLease, countRent, countRentShortTerm,
			countArticles, sizeMin, sizeMax,
			priceSaleInitialMin, priceSaleInitialMax,
			tourExists, isSeismic,
			countElevator, latitude, longitude, region
		);
	}
}