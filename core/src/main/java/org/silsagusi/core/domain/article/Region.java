package org.silsagusi.core.domain.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "regions")
@Getter
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id", nullable = false)
	private Long id;

	@Column(name = "district_code")
	private String cortarNo; // 법정동 코드
	@Column(name = "latitude")
	private Double centerLat; // 화면 정가운데 위도
	@Column(name = "longitude")
	private Double centerLon; // 화면 정가운데 경도
	@Column(name = "area")
	private String cortarName; // 법정동명
	@Column(name = "type")
	private String cortarType; // 법정동 유형
	private String geohash; // 지오 해쉬
	@Column(name = "zigbang_local_code")
	private String zigBangLocalCode; // 직방 지역 코드

	public Region(
		String cortarNo, Double centerLat, Double centerLon,
		String cortarName, String cortarType, String geohash,
		String zigBangLocalCode
	) {
		this.cortarNo = cortarNo;
		this.centerLat = centerLat;
		this.centerLon = centerLon;
		this.cortarName = cortarName;
		this.cortarType = cortarType;
		this.geohash = geohash;
		this.zigBangLocalCode = zigBangLocalCode;
	}
}
