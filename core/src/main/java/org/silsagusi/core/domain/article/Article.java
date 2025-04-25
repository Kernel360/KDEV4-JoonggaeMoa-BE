package org.silsagusi.core.domain.article;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	private Integer price;

	private Integer rentPrice;

	@Column(name = "confirmed_at")
	private LocalDate confirmedAt;

	private Double latitude;

	private Double longitude;

	private String imageUrl;

	private String direction;

	@ElementCollection
	@CollectionTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_tags_id"))
	@Column(name = "tag")
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

	public Article(
		String cortarNo,
		String articleNo,
		String name,
		String realEstateType,
		String tradeType,
		Integer price,
		Integer rentPrice,
		LocalDate confirmedAt,
		Double latitude,
		Double longitude,
		String imageUrl,
		String direction,
		List<String> tags,
		String subwayInfo,
		String companyId,
		String companyName,
		String agentName,
		Region region,
		String lotAddressName,
		String roadAddressName,
		String city,
		String district,
		String town,
		String mainAddressNo,
		String subAddressNo,
		String roadName,
		String mainBuildingNo,
		String subBuildingNo,
		String buildingName,
		String zoneNo
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
}