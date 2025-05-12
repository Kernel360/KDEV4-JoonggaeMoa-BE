package org.silsagusi.api.article.controller.request;

import lombok.Data;

@Data
public class ClusterRequest {
	private double swLat;
	private double neLat;
	private double swLng;
	private double neLng;
	private int zoomLevel;
}
