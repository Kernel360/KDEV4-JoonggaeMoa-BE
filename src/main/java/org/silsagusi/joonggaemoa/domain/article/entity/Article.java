package org.silsagusi.joonggaemoa.domain.article.entity;

import java.util.List;

import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "cortar_no", nullable = false)
	private String cortarNo;

	@Column(name = "article_no", nullable = false)
	private String articleNo;

	private String name;

	@Column(name = "real_estate_type")
	private String realEstateType;

	@Column(name = "trade_type")
	private String tradeType;

	@Column(name = "price_hangul")
	private String priceHangul;

	private String price; // 문자열 그대로 유지 시

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
		String priceHangul, String price, Integer rentPrice, String confirmedAt,
		Double latitude, Double longitude, String imageUrl, String direction,
		List<String> tags, String subwayInfo, String companyId, String companyName, String agentName) {
		this.cortarNo = cortarNo;
		this.articleNo = articleNo;
		this.name = name;
		this.realEstateType = realEstateType;
		this.tradeType = tradeType;
		this.priceHangul = priceHangul;
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
	}

	public static Article createFrom(ClientArticleResponse.Body body) {
		return new Article(
			body.getCortarNo(),
			body.getAtclNo(),
			body.getAtclNm(),
			body.getRletTpNm(),
			body.getTradTpNm(),
			body.getHanPrc(),
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
			body.getRltrNm()
		);
	}
}