package org.silsagusi.joonggaemoa.domain.property.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PropertyEntryController {

	// 매물 등록
	@PostMapping("/api/agents/{agentId}/properties")
	public void registerProperty() {

	}

	// 매물 리스트 조회
	@GetMapping("/api/agents/{agentId}/properties")
	public void getProperties() {

	}

	// 매물 상세 조회
	@GetMapping("/api/agents/{agentId}/properties/{propertyId}")
	public void getPropertyDetail() {

	}

	// 매물 수정
	@PatchMapping("/api/agents/{agentId}/properties/{propertyId}")
	public void updateProperty() {

	}

	// 매물 삭제
	@DeleteMapping("/api/agents/{agentId}/properties/{propertyId}")
	public void deleteProperty() {

	}
}
