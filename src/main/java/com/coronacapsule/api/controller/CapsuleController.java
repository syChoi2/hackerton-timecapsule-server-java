package com.coronacapsule.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.CapsuleDto;
import com.coronacapsule.api.dto.CapsuleNameDto;
import com.coronacapsule.api.service.CapsuleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/capsules")
public class CapsuleController {

	private final CapsuleService capsuleService;
	
	/**
	 * 캡슐 이름 수정
	 * 현재는 capsule ID 없이 user ID로만 찾아서 바꾸지만 나중에 확장하게 되면 capsule ID 구분해서 update 해야한다.
	 */
	@ApiOperation(value="캡슐 이름 수정")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
//	@PatchMapping("/{capsuleId}/name")
	@PatchMapping("/name")
	public void setCapsuleName(@RequestBody CapsuleNameDto capsuleNameDto) {
		
		long userId = 1L;
		
		capsuleService.setCapsuleName(userId, capsuleNameDto);
	}
	
	/**
	 * 캡슐 정보 (색깔별 카운트 + 개수 몇분의 몇)
	 * 현재는 capsule ID 없이 user ID로만 찾아서 바꾸지만 나중에 확장하게 되면 capsule ID 구분해서 update 해야한다.
	 */
	@ApiOperation(value="캡슐 정보")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
//	@PatchMapping("/{capsuleId}/colorCounts")
	@GetMapping
	public ResponseEntity<CapsuleDto> getCapsule() {
		
		long userId = 1L;
		
		CapsuleDto capsuleDto = capsuleService.getCapsule(userId);
		
		return ResponseEntity.ok(capsuleDto);
	}
	
	
}
