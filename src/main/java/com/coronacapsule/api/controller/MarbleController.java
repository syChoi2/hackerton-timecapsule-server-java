package com.coronacapsule.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.MarbleDto;
import com.coronacapsule.api.dto.PostMarbleRequestDto;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.service.MarbleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/marbles")
public class MarbleController {

	@Value("${custom.config.corona_end_flag}")
	private boolean coronaEndFlag;
	

	private final MarbleService marbleService;

	/**
	 * 구슬 - 버킷리스트 내용 목록 출력
	 * 코로나 종식으로 타임캡슐이 열렸을 때만 접근 가능
	 */
	@ApiOperation(value="구슬 - 버킷리스트 목록 (코로나 종식 후)")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@GetMapping
	public ResponseEntity<List<MarbleDto>> getMarbleList(){
		
		if(! coronaEndFlag) {
			throw new BusinessException(ErrorCode.OPEN_NOT_ALLOWED);
		}
		
		long userId = 1L;
		
		List<MarbleDto> marbleList = marbleService.getMarbleList(userId);
		
		return ResponseEntity.ok(marbleList);
	}
	
	/**
	 * 구슬 등록
	 */
	@ApiOperation(value="구슬 등록")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@PostMapping
	public ResponseEntity<?> putMarble(@RequestBody PostMarbleRequestDto marble){
		
		long userId = 1L;
		
		marbleService.putMarble(userId, marble);
		
		return ResponseEntity.ok(null);
	}
	
	
	/**
	 * 구슬 - 버킷 리스트 완료 표시하기
	 * 코로나 종식으로 타임캡슐이 열렸을 때 접근 가능
	 */
	@ApiOperation(value="구슬 - 버킷리스트 체크 (코로나 종식 후)")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@PatchMapping("/{marbleId}/check")
	public ResponseEntity<?> checkWish(@PathVariable("marbleId") long marbleId){
		if(! coronaEndFlag) {
			throw new BusinessException(ErrorCode.OPEN_NOT_ALLOWED);
		}

		long userId = 1L;
		
		marbleService.checkWish(userId, marbleId);
		
		return ResponseEntity.ok(null);
	}
	
}
