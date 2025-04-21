package org.silsagusi.batch.scrape.zigbang.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ZigBangDanjiResponse {

	private String localCode;

	private Integer count;

	private List<ZigBangComplex> list;

	@Data
	private static class ZigBangComplex {

		private Integer areaHoId;

		// 거래 종류
		private String tranType;

		private Integer areaDanjiId;

		// 단지명
		private String areaDanjiName;

		private Integer danjiRoomTypeId;

		// 구
		private String local2;

		// 동
		private String local3;

		private Boolean isPriceRange;

		// 보증금(최소금액)
		private Integer depositMin;

		// 월세(최소금액)
		private Integer rentMin;

		private RoomTypeTitle roomTypeTitle;

		private Double sizeContractM2;

		private Double sizeM2;

		private String dong;

		private String floor;

		// 매물 설명
		private String itemTitle;

		private Integer zzimCount;

		private Boolean isZzim;

		private Boolean isActualItemChecked;

		private String thumbnailUrl;

		private Boolean isFloorPlanThumbnail;

		private Integer itemCount;

		private List<ItemId> itemIdList;

		// 이미지 URL 리스트
		private List<String> agentThumbnailUrls;
	}

	@Data
	private static class RoomTypeTitle {

		private String m2;

		private String p;
	}

	@Data
	private static class ItemId {

		// 매물 출처
		private String itemSource;

		private Integer itemId;

	}
}
