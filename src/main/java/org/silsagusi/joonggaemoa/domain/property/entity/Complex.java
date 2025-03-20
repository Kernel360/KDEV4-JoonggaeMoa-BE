package org.silsagusi.joonggaemoa.domain.property.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "properties")
@Getter
public class Complex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complex_id")
	private Long id;

	private String name;

	private Integer unitCount;
}
