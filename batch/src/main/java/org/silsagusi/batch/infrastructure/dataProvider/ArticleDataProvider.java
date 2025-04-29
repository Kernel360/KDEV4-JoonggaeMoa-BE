package org.silsagusi.batch.infrastructure.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.infrastructure.external.AddressResponse;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandArticleResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}

	public Article createNaverLandArticle(
		NaverLandArticleResponse.NaverLandArticle naverLandArticle,
		AddressResponse addressResponse,
		Region region,
		Complex complex
	) {
		return Article.create(
			naverLandArticle.getAtclNo(),
			naverLandArticle.getCortarNo(),
			naverLandArticle.getAtclNm(),
			naverLandArticle.getRletTpCd(),
			naverLandArticle.getRletTpNm(),
			naverLandArticle.getTradTpNm(),
			naverLandArticle.getFlrInfo(),
			naverLandArticle.getPrc(),
			naverLandArticle.getRentPrc(),
			naverLandArticle.getSpc1(),
			naverLandArticle.getSpc2(),
			naverLandArticle.getDirection(),
			naverLandArticle.getAtclCfmYmd(),
			naverLandArticle.getRepImgUrl() == null ? null :
				"https://landthumb-phinf.pstatic.net" + naverLandArticle.getRepImgUrl(),
			naverLandArticle.getLat(),
			naverLandArticle.getLng(),
			naverLandArticle.getAtclFetrDesc(),
			naverLandArticle.getCpNm(),
			naverLandArticle.getRltrNm(),
			naverLandArticle.getSbwyInfo(),
			naverLandArticle.getTradeCheckedByOwner(),
			region,
			complex,
			addressResponse.getLotAddress(),
			addressResponse.getRoadAddress(),
			addressResponse.getCity(),
			addressResponse.getDistrict(),
			addressResponse.getRegion()
		);
	}

	public Article createZigBangItemCatalog(
		ZigBangItemCatalogResponse.ZigBangItemCatalog item,
		ZigBangDanjiResponse danji,
		AddressResponse addressResponse,
		Region region, Complex complex, String cortarNo

	) {
		return Article.create(
			String.valueOf(item.getAreaDanjiId()),
			cortarNo,
			item.getAreaDanjiName() + " " + item.getDong(),
			"A01",
			"아파트",
			mapTranType(item.getTranType()),
			item.getFloor(),
			item.getDepositMin(),
			item.getRentMin(),
			item.getRoomTypeTitle().getM2(),
			String.valueOf(item.getSizeM2()),
			null,
			danji.getFiltered().get(0).get사용승인일(),
			Objects.equals(item.getThumbnailUrl(), "") ? null :
				item.getThumbnailUrl() + "?w=1000",
			danji.getFiltered().get(0).getLat(),
			danji.getFiltered().get(0).getLng(),
			item.getItemTitle(),
			mapItemType(item.getItemType()),
			"직방",
			null,
			mapIsChecked(item.getItemType()),
			region,
			complex,
			addressResponse.getLotAddress(),
			addressResponse.getRoadAddress(),
			addressResponse.getCity(),
			addressResponse.getDistrict(),
			addressResponse.getRegion()
		);
	}

	// 거래 유형
	private String mapTranType(String tranType) {
		if (Objects.equals(tranType, "trade")) {
			return "매매";
		} else if (Objects.equals(tranType, "charter")) {
			return "전세";
		} else if (Objects.equals(tranType, "rental")) {
			return "월세";
		} else {
			return null;
		}
	}

	// 매물 출처
	private String mapItemType(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return "직방 파트너 계약 매물"; // 직방 제휴 매물
		} else if (Objects.equals(itemType, "self_ad")) {
			return "직방 개인 광고 매물"; // 개인 매물, 유료 광고 적용됨
		} else {
			return "직방 검증 완료 매물";
		}
	}

	// 확인매물여부
	private Boolean mapIsChecked(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return true; // 직방 제휴 매물
		} else
			return Objects.equals(itemType, "self_ad"); // 개인 매물, 유료 광고 적용됨
	}
}
