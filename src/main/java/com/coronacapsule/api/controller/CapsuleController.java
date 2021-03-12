package com.coronacapsule.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.CapsuleDto;
import com.coronacapsule.api.dto.CapsuleNameDto;
import com.coronacapsule.api.dto.JwtAuthentication;
import com.coronacapsule.api.service.CapsuleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/capsules")
public class CapsuleController {

	private final CapsuleService capsuleService;
	

	@Value("${custom.config.corona_end_flag}")
	private boolean coronaEndFlag;
	/**
	 * 좌물쇠 상태 확인 
	 * 코로나 끝나면 true 로 내려줌
	 */
	@ApiOperation(value="좌물쇠 상태 확인 (코로나 끝나면 true 로 내려줌)")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
//	@PatchMapping("/{capsuleId}/name")
	@GetMapping("/open")
	public ResponseEntity<Boolean> getOpenCapsuleFlag() {

		return ResponseEntity.ok(coronaEndFlag);
	}
	
	/**
	 * 캡슐 이름 수정
	 * 현재는 capsule ID 없이 user ID로만 찾아서 바꾸지만 나중에 확장하게 되면 capsule ID 구분해서 update 해야한다.
	 */
	@ApiOperation(value="캡슐 이름 수정")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
//	@PatchMapping("/{capsuleId}/name")
	@PatchMapping("/name")
	public void setCapsuleName(@RequestBody CapsuleNameDto capsuleNameDto, @ApiIgnore @AuthenticationPrincipal JwtAuthentication authentication) {
		
		long userId = authentication.userId;
		
		capsuleService.setCapsuleName(userId, capsuleNameDto);
	}
	
	/**
	 * 캡슐 정보 (색깔별 카운트 + 개수 몇분의 몇)
	 * 현재는 capsule ID 없이 user ID로만 찾아서 바꾸지만 나중에 확장하게 되면 capsule ID 구분해서 update 해야한다.
	 */
	@ApiOperation(value="캡슐 정보")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
//	@PatchMapping("/{capsuleId}/colorCounts")
	@GetMapping
	public ResponseEntity<CapsuleDto> getCapsule(@ApiIgnore @AuthenticationPrincipal JwtAuthentication authentication) {
		
		long userId = authentication.userId;
		
		CapsuleDto capsuleDto = capsuleService.getCapsule(userId);
		
		return ResponseEntity.ok(capsuleDto);
	}
	
	
}
