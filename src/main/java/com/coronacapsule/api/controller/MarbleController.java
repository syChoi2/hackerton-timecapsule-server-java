package com.coronacapsule.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.JwtAuthentication;
import com.coronacapsule.api.dto.MarbleDto;
import com.coronacapsule.api.dto.MarbleListResponseDto;
import com.coronacapsule.api.dto.PostMarbleRequestDto;
import com.coronacapsule.api.enums.MarbleColor;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.service.CapsuleService;
import com.coronacapsule.api.service.MarbleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/marbles")
public class MarbleController {


	private final MarbleService marbleService;
	private final CapsuleService capsuleService;

	/**
	 * 구슬 - 버킷리스트 내용 목록 출력
	 * 코로나 종식으로 타임캡슐이 열렸을 때만 접근 가능
	 * @throws Exception 
	 */
	@ApiOperation(value="구슬 - 버킷리스트 목록 (코로나 종식 후)")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
	@GetMapping
	public ResponseEntity<MarbleListResponseDto> getMarbleList(@ApiIgnore @AuthenticationPrincipal JwtAuthentication authentication, String marbleColor) throws Exception{
		
		if(! capsuleService.getOpenCapsuleFlag()) {
			throw new BusinessException(ErrorCode.OPEN_NOT_ALLOWED);
		}
		MarbleColor marbleColorAsEmun = null;
		if(marbleColor!=null) {
			marbleColorAsEmun = MarbleColor.lookup(Integer.parseInt(marbleColor));
		}
		
		long userId = authentication.userId;
		
		MarbleListResponseDto marbleListResponseDto = marbleService.getMarbleList(userId, marbleColorAsEmun);
		
		return ResponseEntity.ok(marbleListResponseDto);
	}
	
	/**
	 * 구슬 - 버킷리스트 내용 목록 출력
	 * 코로나 종식으로 타임캡슐이 열렸을 때만 접근 가능
	 * @throws Exception 
	 */
	@ApiOperation(value="구슬 - 완료됨 목록 (코로나 종식 후)")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
	@GetMapping("/checked")
	public ResponseEntity<List<MarbleDto>> getChekcedMarbleList(@ApiIgnore @AuthenticationPrincipal JwtAuthentication authentication) throws Exception{
		
		if(! capsuleService.getOpenCapsuleFlag()) {
			throw new BusinessException(ErrorCode.OPEN_NOT_ALLOWED);
		}
		
		long userId = authentication.userId;
		
		List<MarbleDto> marbleDtoList = marbleService.getChekcedMarbleList(userId);
		
		return ResponseEntity.ok(marbleDtoList);
	}
	
	/**
	 * 구슬 등록
	 * @throws Exception 
	 */
	@ApiOperation(value="구슬 등록")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
	@PostMapping
	public ResponseEntity<?> putMarble(@ApiParam(value = "marble\r\nmarbleColor : String, 숫자 둘다 허용 [RED(0), YELLOW(1), BLUE(2), GREEN(3), PURPLE(4)]", required = true)@RequestBody PostMarbleRequestDto marble, @ApiIgnore @AuthenticationPrincipal JwtAuthentication authentication) throws Exception{
		
		long userId = authentication.userId;
		
		marbleService.putMarble(userId, marble);
		
		return ResponseEntity.ok(null);
	}
	
	
	/**
	 * 구슬 - 버킷 리스트 완료 표시하기
	 * 코로나 종식으로 타임캡슐이 열렸을 때 접근 가능
	 * @throws Exception 
	 */
	@ApiOperation(value="구슬 - 버킷리스트 체크 (코로나 종식 후)")
	@ApiImplicitParam(name = "X-ACCESS-TOKEN", paramType = "header", required = true, value = "access token")
	@PatchMapping("/{marbleId}/check")
	public ResponseEntity<?> checkWish(@PathVariable("marbleId") long marbleId, @ApiIgnore  @AuthenticationPrincipal JwtAuthentication authentication) throws Exception{
		if(! capsuleService.getOpenCapsuleFlag()) {
			throw new BusinessException(ErrorCode.OPEN_NOT_ALLOWED);
		}

		long userId = authentication.userId;
		
		marbleService.checkWish(userId, marbleId);
		
		return ResponseEntity.ok(null);
	}
	
}
