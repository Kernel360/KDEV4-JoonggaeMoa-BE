package org.silsagusi.core.domain.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "regions")
@Getter
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id", nullable = false)
	private Long id;

	@Column(name = "bjd_code")
	private String cortarNo; // 법정동 코드
	@Column(name = "latitude")
	private Double centerLat; // 화면 정가운데 위도
	@Column(name = "longitude")
	private Double centerLon; // 화면 정가운데 경도
	@Column(name = "address")
	private String cortarName; // 법정동명
	@Column(name = "category")
	private String cortarType; // 법정동 유형
	private String geohash; // 지오 해쉬
}
