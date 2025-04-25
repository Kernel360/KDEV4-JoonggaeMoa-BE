package org.silsagusi.batch.infrastructure.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandArticleResponse;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}

	public static Article createNaverLandArticle(
		NaverLandArticleResponse.NaverLandArticle naverLandArticle,
		KakaoMapAddressResponse kakaoMapAddressResponse,
		Region region,
		Complex complex
	) {
		return new Article(
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
			naverLandArticle.getRepImgUrl() == null ? null : "https://landthumb-phinf.pstatic.net" + naverLandArticle.getRepImgUrl(),
			naverLandArticle.getLat(),
			naverLandArticle.getLng(),
			naverLandArticle.getAtclFetrDesc(),
			naverLandArticle.getCpNm(),
			naverLandArticle.getRltrNm(),
			naverLandArticle.getSbwyInfo(),
			naverLandArticle.getTradeCheckedByOwner(),
			region,
			complex,
			kakaoMapAddressResponse.getLotAddress(),
			kakaoMapAddressResponse.getRoadAddress(),
			kakaoMapAddressResponse.getCity(),
			kakaoMapAddressResponse.getDistrict(),
			kakaoMapAddressResponse.getRegion()
		);
	}

	public static Article createZigBangItemCatalog(
		ZigBangItemCatalogResponse.ZigBangItemCatalog item,
		ZigBangDanjiResponse danji,
		KakaoMapAddressResponse kakaoMapAddressResponse,
		Region region, Complex complex

	) {
		return new Article(
			String.valueOf(item.getAreaDanjiId()),
			null,
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
			item.getThumbnailUrl() +"?w=1000",
			danji.getFiltered().get(0).getLat(),
			danji.getFiltered().get(0).getLng(),
			item.getItemTitle(),
			mapItemType(item.getItemType()),
			"직방",
			null,
			mapIsChecked(item.getItemType()),
			region,
			complex,
			kakaoMapAddressResponse.getLotAddress(),
			kakaoMapAddressResponse.getRoadAddress(),
			kakaoMapAddressResponse.getCity(),
			kakaoMapAddressResponse.getDistrict(),
			kakaoMapAddressResponse.getRegion()
		);
	}

	// 거래 유형
	private static String mapTranType(String tranType) {
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
	private static String mapItemType(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return "직방 파트너 계약 매물"; // 직방 제휴 매물
		} else if (Objects.equals(itemType, "self_ad")) {
			return "직방 개인 광고 매물"; // 개인 매물, 유료 광고 적용됨
		} else {
			return "직방 검증 완료 매물";
		}
	}

	// 확인매물여부
	private static Boolean mapIsChecked(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return true; // 직방 제휴 매물
		} else return Objects.equals(itemType, "self_ad"); // 개인 매물, 유료 광고 적용됨
	}
}
