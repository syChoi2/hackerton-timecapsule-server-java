package com.coronacapsule.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.LoginRequestDto;
import com.coronacapsule.api.dto.LoginResponseDto;
import com.coronacapsule.api.dto.NicknameDto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	/**
	 * test
	 */


	/**
	 * 로그인
	 * 최초 가입일 경우 닉네임을 받기 위한 flag 값 필요
	 *
	 * return
	 * 1. 토큰 + 캡슐정보
	 * 2. new user t/f
	 */
	@ApiOperation(value="로그인")
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
		
		return ResponseEntity.ok(null);
		
	}
	
	/**
	 * 최초 로그인 시 닉네임 등록/수정
	 * 중복일 시 오류 메시지 출력
	 */
	@ApiOperation(value="닉네임 등록/수정")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@PostMapping("/nickname")
	public ResponseEntity<LoginResponseDto> setNickname(@RequestBody NicknameDto nicknameDto){
		
		return ResponseEntity.ok(null);
		
	}
	
	
}

