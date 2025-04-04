package org.silsagusi.joonggaemoa.request.naverland.client;

import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientComplexResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverLandApiClient {

	private final WebClient naverWebClient;

	/* 매물 유형 (real estate type code)
	 * 아파트 : APT
	 * 오피스텔 : OPST
	 * 빌라 : VL
	 * 아파트분양권 : ABYG
	 * 오피스텔분양권 : OBYG
	 * 재건축 : JGC
	 * 전원주택 : JWJT
	 * 단독/다가구 : DDDGG
	 * 상가주택 : SGJT
	 * 한옥주택 : HOJT
	 * 재개발 : JGB
	 * 원룸 : OR
	 * 고시원 : GSW
	 * 상가 : SG
	 * 사무실 : SMS
	 * 공장/창고 : GJCG
	 * 건물 : GM
	 * 토지 : TJ
	 * 지식산업센터 : APTHGJ
	 */
	private static final String RLET_TP_CD
		= "APT:OPST:VL:ABYG:OBYG:JGC:JWJT:DDDGG:SGJT:HOJT:JGB:OR:GSW:SG:SMS:GJCG:GM:TJ:APTHGJ";

	/* 거래 유형 (trade type code)
	 * 매매 : A1
	 * 전세 : B1
	 * 월세 : B2
	 * 단기임대 : B3
	 */
	private static final String TRAD_TP_CD = "A1:B1:B2:B3";

	/* 지도 확대 단계 (zoom level)
	 * zoom level 15 : 1:4,000
	 * zoom level 16 : 1:2,000
	 * zoom level 17 : 1:1,000
	 */
	private static final String ZOOM_LEVEL = "15";

	public <T> T fetchList(String path, String lat, String lon, String cortarNo, String page, Class<T> responseType) {
		return naverWebClient.get()
			.uri(uriBuilder -> uriBuilder.path(path)
				.queryParam("rletTpCd", RLET_TP_CD)
				.queryParam("tradTpCd", TRAD_TP_CD)
				.queryParam("z", ZOOM_LEVEL)
				.queryParam("lat", lat)
				.queryParam("lon", lon)
				.queryParam("btm", (String.valueOf(Double.parseDouble(lat) - 0.015)))
				.queryParam("lft", (String.valueOf(Double.parseDouble(lon) - 0.02)))
				.queryParam("top", (String.valueOf(Double.parseDouble(lat) + 0.015)))
				.queryParam("rgt", (String.valueOf(Double.parseDouble(lon) + 0.02)))
				.queryParam("cortarNo", cortarNo)
				.queryParam("page", page)
				.build())
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(responseType)
			.block();
	}

	public ClientArticleResponse fetchArticleList(String page, String lat, String lon, String cortarNo) {
		return fetchList("/cluster/ajax/articleList", lat, lon, cortarNo, page, ClientArticleResponse.class);
	}

	public ClientComplexResponse fetchComplexList(String page, String lat, String lon, String cortarNo) {
		return fetchList("/cluster/ajax/complexList", lat, lon, cortarNo, page, ClientComplexResponse.class);
	}

}
