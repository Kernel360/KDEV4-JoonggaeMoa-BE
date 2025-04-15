package org.silsagusi.joonggaemoa.api.article.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "complexes")
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Complex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complex_id", nullable = false)
	private Long id;

	// house complex name 단지명
	@Column(name = "name")
	private String hscpNm;

	// house type name 유형 (다세대, 연립 등)
	@Column(name = "type")
	private String hscpTypeNm;

	// use approval date 준공일
	@Column(name = "approved_at")
	private String useAprvYmd;

	public Complex(String hscpNm, String hscpTypeNm, String useAprvYmd) {
		this.hscpNm = hscpNm;
		this.hscpTypeNm = hscpTypeNm;
		this.useAprvYmd = useAprvYmd;
	}
}