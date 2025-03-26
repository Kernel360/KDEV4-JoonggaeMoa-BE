package org.silsagusi.joonggaemoa.domain.property.entity;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@ToString
@Entity(name = "properties")
@Getter
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "property_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	private String sido;

	private String gugun;

	private String dongeupmeon;

	private String roadName;
}
