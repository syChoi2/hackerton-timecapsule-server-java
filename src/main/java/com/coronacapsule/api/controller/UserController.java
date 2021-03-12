package com.coronacapsule.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.LoginRequestDto;
import com.coronacapsule.api.dto.LoginResponseDto;
import com.coronacapsule.api.dto.SignUpDto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	/**
	 * 회원 유무 확인
	 */
	@ApiOperation(value="로그인")
	@GetMapping("/exists")
	public ResponseEntity<Boolean> exists(String socialToken){
		
		return ResponseEntity.ok(null);
		
	}
	
	/**
	 * 로그인
	 *
	 * return
	 * 1. 토큰 + 캡슐정보
	 */
	@ApiOperation(value="로그인")
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
		
		return ResponseEntity.ok(null);
		
	}
	
	/**
	 * 회원가입
	 * 
	 * 회원가입 후 로그인까지 진행
	 */
	@ApiOperation(value="회원가입")
	@PatchMapping("/signUp")
	public ResponseEntity<LoginResponseDto> signUp(@RequestBody SignUpDto signUpDto){
		
		return ResponseEntity.ok(null);
		
	}	
	
	/**
	 * 닉네임 수정
	 * 중복일 시 오류 메시지 출력
	 */
	@ApiOperation(value="닉네임 수정")
	@ApiImplicitParam(name = "Autentication", paramType = "header", required = true, value = "access token")
	@PatchMapping("/nickname")
	public ResponseEntity<?> setNickname(@RequestBody SignUpDto nicknameDto){
		
		return ResponseEntity.ok(null);
		
	}
	
	
}

