package org.silsagusi.core.domain.article;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cluster {
	private double lat;
	private double lng;
	private long count;
}