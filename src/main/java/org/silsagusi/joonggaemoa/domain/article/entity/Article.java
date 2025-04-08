package org.silsagusi.joonggaemoa.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.global.address.dto.AddressDto;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "articles")
@Getter
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

	@Column(name = "lot_address")
	private String lotAddressName;

	@Column(name = "road_address")
	private String roadAddressName;

	private String city;

	private String district;

	private String town;

	private String mainAddressNo;

	private String subAddressNo;

	private String roadName;

	private String mainBuildingNo;

	private String subBuildingNo;

	private String buildingName;

	@Column(name = "zip_code")
	private String zoneNo;

	public Article(String cortarNo, String articleNo, String name, String realEstateType, String tradeType,
	               String price, Integer rentPrice, String confirmedAt, Double latitude, Double longitude,
	               String imageUrl, String direction, List<String> tags, String subwayInfo,
	               String companyId, String companyName, String agentName, Region region,
	               String lotAddressName, String roadAddressName, String city, String district, String town,
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
		this.lotAddressName = lotAddressName;
		this.roadAddressName = roadAddressName;
		this.city = city;
		this.district = district;
		this.town = town;
		this.mainAddressNo = mainAddressNo;
		this.subAddressNo = subAddressNo;
		this.roadName = roadName;
		this.mainBuildingNo = mainBuildingNo;
		this.subBuildingNo = subBuildingNo;
		this.buildingName = buildingName;
		this.zoneNo = zoneNo;
	}

	public static Article createFrom(ClientArticleResponse.Body body, Region region, AddressDto addressDto) {
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
			addressDto.getLotAddress(),
			addressDto.getRoadAddress(),
			addressDto.getCity(),
			addressDto.getDistrict(),
			addressDto.getRegion(),
			addressDto.getMainAddressNo(),
			addressDto.getSubAddressNo(),
			addressDto.getRoadName(),
			addressDto.getMainBuildingNo(),
			addressDto.getSubBuildingNo(),
			addressDto.getBuildingName(),
			addressDto.getZoneNo()
		);
	}
}