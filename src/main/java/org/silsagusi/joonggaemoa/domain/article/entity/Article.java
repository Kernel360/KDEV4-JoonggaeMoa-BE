package org.silsagusi.joonggaemoa.domain.article.entity;

import java.util.List;

import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;

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

	@Column(name = "cortar_no", nullable = false)
	private String cortarNo;

	@Column(name = "article_no", nullable = false)
	private String articleNo;

	private String name;

	@Column(name = "real_estate_type")
	private String realEstateType;

	@Column(name = "trade_type")
	private String tradeType;

	private String price;

	@Column(name = "rent_price")
	private Integer rentPrice;

	@Column(name = "confirmed_at")
	private String confirmedAt;

	private Double latitude;

	private Double longitude;

	@Column(name = "image_url")
	private String imageUrl;

	private String direction;

	@ElementCollection
	private List<String> tags;

	@Column(name = "subway_info")
	private String subwayInfo;

	@Column(name = "company_id")
	private String companyId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "agent_name")
	private String agentName;

	public Article(String cortarNo, String articleNo, String name, String realEstateType, String tradeType,
		String price, Integer rentPrice, String confirmedAt, Double latitude, Double longitude, String imageUrl,
		String direction, List<String> tags, String subwayInfo, String companyId, String companyName,
		String agentName, Region region) {
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
	}

	public static Article createFrom(ClientArticleResponse.Body body, Region region) {
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
			region
		);
	}
}