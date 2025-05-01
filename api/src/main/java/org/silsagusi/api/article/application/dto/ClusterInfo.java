package org.silsagusi.api.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClusterInfo {
	private double lat;
	private double lng;
	private long count;
}