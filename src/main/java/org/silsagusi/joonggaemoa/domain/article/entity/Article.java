package org.silsagusi.joonggaemoa.domain.article.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.Coord2AddressResponse;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "articles")
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	@Column(nullable = false)
	private String cortarNo;

	@Column(nullable = false)
	private String articleNo;

	private String name;

	private String realEstateType;

	private String tradeType;

	private String price;

	private Integer rentPrice;

	private String confirmedAt;

	private Double latitude;

	private Double longitude;

	private String imageUrl;

	private String direction;

	@ElementCollection
	private List<String> tags;

	private String subwayInfo;

	private String companyId;

	private String companyName;

	private String agentName;

	String 지번;

	String 도로명;

	@Column(name = "lot_address")
	private String addressName;

	@Column(name = "city")
	private String region1DepthName;

	@Column(name = "district")
	private String region2DepthName;

	@Column(name = "section")
	private String region3DepthName;

	private String mainAddressNo;

	private String subAddressNo;

	private String roadName;

	private String mainBuildingNo;

	private String subBuildingNo;

	private String buildingName;

	private String zoneNo;

	public Article(String cortarNo, String articleNo, String name, String realEstateType, String tradeType,
	               String price, Integer rentPrice, String confirmedAt, Double latitude, Double longitude,
	               String imageUrl, String direction, List<String> tags, String subwayInfo,
	               String companyId, String companyName, String agentName, Region region,
	               String addressName, String region1DepthName, String region2DepthName, String region3DepthName,
	               String mainAddressNo, String subAddressNo,
	               String roadName, String mainBuildingNo, String subBuildingNo, String buildingName, String zoneNo
	) {
		this.cortarNo = cortarNo;
		this.articleNo = articleNo;
		this.name = name;
		this.realEstateType = realEstateType;
		this.tradeType = tradeType;
		this.price = price;
		this.rentPrice = rentPrice;
		this.confirmedAt = confirmedAt;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imageUrl = imageUrl;
		this.direction = direction;
		this.tags = tags;
		this.subwayInfo = subwayInfo;
		this.companyId = companyId;
		this.companyName = companyName;
		this.agentName = agentName;
		this.region = region;
		this.addressName = addressName;
		this.region1DepthName = region1DepthName;
		this.region2DepthName = region2DepthName;
		this.region3DepthName = region3DepthName;
		this.mainAddressNo = mainAddressNo;
		this.subAddressNo = subAddressNo;
		this.roadName = roadName;
		this.mainBuildingNo = mainBuildingNo;
		this.subBuildingNo = subBuildingNo;
		this.buildingName = buildingName;
		this.zoneNo = zoneNo;
	}

	public static Article createFrom(ClientArticleResponse.Body body, Region region,
		Coord2AddressResponse.Address addr, Coord2AddressResponse.RoadAddress roadAddr
		) {
		return new Article(
			body.getCortarNo(),
			body.getAtclNo(),
			body.getAtclNm(),
			body.getRletTpNm(),
			body.getTradTpNm(),
			body.getPrc() != null ? body.getPrc().toString() : null,
			body.getRentPrc(),
			body.getAtclCfmYmd(),
			body.getLat(),
			body.getLng(),
			body.getRepImgUrl(),
			body.getDirection(),
			body.getTagList(),
			body.getSbwyInfo(),
			body.getCpid(),
			body.getCpNm(),
			body.getRltrNm(),
			region,
			addr.getAddressName(),
			addr.getRegion1DepthName(),
			addr.getRegion2DepthName(),
			addr.getRegion3DepthName(),
			addr.getMainAddressNo(),
			addr.getSubAddressNo(),
			roadAddr.getRoadName(),
			roadAddr.getMainBuildingNo(),
			roadAddr.getSubBuildingNo(),
			roadAddr.getBuildingName(),
			roadAddr.getZoneNo()
			);
	}
}