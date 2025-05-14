package org.silsagusi.api.article.infrastructure.dataprovider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.dto.BoundingBox;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.silsagusi.api.article.infrastructure.repository.ClusterRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClusterDataProvider {

	// 클러스터 반지름을 픽셀 단위로 정의
	private static final double CLUSTER_RADIUS_PX = 60;

	private final ClusterRepository clusterRepository;

	public List<MarkerProjection> getMarkers(BoundingBox box) {
		return clusterRepository.findMarkers(box);
	}

	public List<ClusterProjection> getClusters(BoundingBox box, int zoomLevel) {
		// 1) 카카오 공식: 레벨 3에서 1px = 1m, 레벨이 하나 올라갈 때마다 1px에 대응되는 미터 수는 2배가 됨
		//    meterPerPixel = 2^(zoomLevel - 3)
		double meterPerPixel = Math.pow(2, zoomLevel - 3);

		// 2) 클러스터 반지름(px) → 반지름(m)
		double radiusInMeters = CLUSTER_RADIUS_PX * meterPerPixel;

		// 3) 위·경도(degree)로 환산 (지구 둘레 약 40075016.686m 기준)
		double degreesPerMeter = 360.0 / 40075016.686;
		//    그리드 한 변의 길이(대략 반지름 * 2): 반지름(m) * 2 → degree
		double gridSizeInDegrees = radiusInMeters * 2 * degreesPerMeter;

		return clusterRepository.findClusters(box, gridSizeInDegrees);
	}
}
