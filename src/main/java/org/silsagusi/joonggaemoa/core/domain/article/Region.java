package org.silsagusi.joonggaemoa.core.domain.article;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "regions")
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id", nullable = false)
	private Long id;

	// 법정동 코드
	private String cortarNo;

	// 화면 정가운데 위도
	@Column(name = "latitude")
	private Double centerLat;

	// 화면 정가운데 경도
	@Column(name = "longitude")
	private Double centerLon;

	// 법정동명
	@Column(name = "area")
	private String cortarName;

	// 법정동 유형
	@Column(name = "type")
	private String cortarType;

	public Region(String cortarNo, Double centerLat, Double centerLon, String cortarName, String cortarType) {
		this.cortarNo = cortarNo;
		this.centerLat = centerLat;
		this.centerLon = centerLon;
		this.cortarName = cortarName;
		this.cortarType = cortarType;
	}
}
