package com.coronacapsule.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.LoginRequestDto;
import com.coronacapsule.api.dto.LoginResponseDto;
import com.coronacapsule.api.dto.SignUpDto;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.service.KakaoService;
import com.coronacapsule.api.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
	
	private final KakaoService kakaoService;
	private final UserService userService;

	/**
	 * 회원 유무 확인
	 */
	@ApiOperation(value="회원가입 여부 확인")
	@GetMapping("/exists")
	public ResponseEntity<Boolean> exists(@RequestHeader String socialToken){
		long userId ;
		boolean isMember = true;
		System.out.println(socialToken);
		
		try {
			userId= kakaoService.userIdFromKakao(socialToken);
		}catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);

		}
		String social_id = Long.toString(userId);
		System.out.println(userId);
		
		try {
			isMember = userService.getUser(social_id);
		}catch(Exception e) {
			e.printStackTrace();
			 new BusinessException("회원정보 조회 실패 ", ErrorCode.NOT_FOUND);
		}
		
		
	
		
		return ResponseEntity.ok(isMember);
		
	}
	
	/**
	 * 로그인
	 *
	 * return
	 * 1. 토큰 + 캡슐정보
	 */
	@ApiOperation(value="로그인")
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestHeader String socialToken){
		long userId ;
		String jwtToken="";

		try {
			userId= kakaoService.userIdFromKakao(socialToken);
		}catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);

		}
		String social_id = Long.toString(userId);
		
		
		try {
			jwtToken = userService.userLogin(userId);
		}catch(Exception e) {
			e.printStackTrace();
            throw new BusinessException("로그인에 실패하였습니다.", ErrorCode.TOKEN_ERROR);

		}
		return ResponseEntity.ok("jwtToken: "+jwtToken);
		
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