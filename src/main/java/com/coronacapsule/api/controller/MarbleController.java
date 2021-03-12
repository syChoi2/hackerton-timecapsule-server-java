package com.coronacapsule.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.MarbleDto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/marbles")
public class MarbleController {

	@Value("${custom.config.corona_end_flag}")
	private boolean coronaEndFlag;
	

	/**
	 * 구슬 등록
	 * 특이사항으로 21개 이하만 등록 가능
	 * 
	 * @return 
	 * 새로 만들어진 marble 정보?
	 */
	@ApiOperation(value="구슬 등록 -> return 값이 필요한지?")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@PostMapping
	public ResponseEntity<?> putMarble(@ApiParam(value = "marble\r\n(wishChecked 안 보내주셔도 됩니다.)") @RequestBody MarbleDto marble){
		
		return ResponseEntity.ok(null);
	}
	
	/**
	 * 구슬 - 버킷리스트 내용 목록 출력
	 * 코로나 종식으로 타임캡슐이 열렸을 때만 접근 가능
	 * 
	 * @return List<MarbleDto>
	 */
	@ApiOperation(value="구슬 - 버킷리스트 목록 (코로나 종식 후)")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@GetMapping
	public ResponseEntity<List<MarbleDto>> getMarbleList(){
		if(! coronaEndFlag) {
			// throw error
		}
		
		return ResponseEntity.ok(null);
	}
	
	/**
	 * 구슬 - 버킷 리스트 완료 표시하기
	 * 코로나 종식으로 타임캡슐이 열렸을 때 접근 가능
	 * 
	 * TODO: 메인 화면에서 코로나 종식 후에도 타임캡슐 에니메이션을 보여줄 거면 
	 * 		  이걸 체크했을 때 구슬 카운트가 줄어야하는지 논의 필요
	 *  
	 * @return null
	 */
	@ApiOperation(value="구슬 - 버킷리스트 체크 (코로나 종식 후)")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@PatchMapping("/{marbleId}/check")
	public ResponseEntity<?> checkWish(){
		if(! coronaEndFlag) {
			// throw error
		}
		
		return ResponseEntity.ok(null);
	}
	
}
