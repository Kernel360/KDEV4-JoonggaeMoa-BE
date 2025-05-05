package org.silsagusi.batch.zigbang.infrastructure.dto;

import lombok.Data;

import java.util.List;

@Data
public class ZigBangItemCatalogResponse {

	private String localCode;
	private Integer count;
	private List<ZigBangItemCatalog> list;
	private String local1;
	private String local2;
	private String local3;
	private String step;
	private Double lat;
	private Double lng;

	@Data
	public static class ZigBangItemCatalog {
		private Integer areaHoId;
		private String tranType; // 거래 종류
		private String itemType; // 매물 유형
		private Integer areaDanjiId;
		private String areaDanjiName; // 단지명
		private Integer danjiRoomTypeId;
		private String local2; // 구
		private String local3; // 동
		private Boolean isPriceRange;
		private Integer depositMin; // 보증금(최소금액)
		private Integer rentMin; // 월세(최소금액)
		private RoomTypeTitle roomTypeTitle;
		private Double sizeContractM2;
		private Double sizeM2;
		private String dong;
		private String floor;
		private String itemTitle; // 매물 설명
		private Integer zzimCount;
		private Boolean isZzim;
		private Boolean isActualItemChecked;
		private String thumbnailUrl;
		private Boolean isFloorPlanThumbnail;
		private Integer itemCount;
		private List<ItemId> itemIdList;
		private List<String> agentThumbnailUrls; // 이미지 URL 리스트
	}

	@Data
	public static class RoomTypeTitle {
		private String m2;
		private String p;
	}

	@Data
	public static class ItemId {
		private String itemSource; // 매물 출처
		private Integer itemId;
	}
}
