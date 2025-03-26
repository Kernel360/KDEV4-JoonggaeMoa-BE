package org.silsagusi.joonggaemoa.domain.property.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@ToString
@Entity(name = "complexes")
@Getter
public class Complex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complex_id")
	private Long id;

	private String name;

	private Integer unitCount;
}
