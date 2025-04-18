package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.scrape.zigbang.client.ZigbangApiClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZigBangAptResponseService {

	private final ZigbangApiClient zigbangApiClient;

	@Async("scrapExecutor")
	public void scrapApt() {

	}

}
