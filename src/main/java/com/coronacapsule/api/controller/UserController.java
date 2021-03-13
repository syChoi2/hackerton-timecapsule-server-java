package com.coronacapsule.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronacapsule.api.dto.PatchNicknameRequest;
import com.coronacapsule.api.dto.SignUpDto;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.service.JwtService;
import com.coronacapsule.api.service.KakaoService;
import com.coronacapsule.api.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	private final KakaoService kakaoService;
	private final UserService userService;
	private final JwtService jwtService;

	/**
	 * 회원 유무 확인
	 */
	@ApiOperation(value = "회원가입 여부 확인")
	@GetMapping("/exists")
	public ResponseEntity<Boolean> exists(@RequestHeader("social-token") String socialToken) {
//		long userId;		//social id 와 user id 구분하기 위해 social 토큰에서 가져온 id는 social id로 선언하겠습니다. 
		String socialId;	//이 때 소셜 아이디는 스트링
		boolean isMember = true;
		System.out.println(socialToken);

		try {
			
//			userId = kakaoService.userIdFromKakao(socialToken);	//social id 와 user id 구분하기 위해 social 토큰에서 가져온 id는 social id로 선언하겠습니다.
			socialId = kakaoService.userIdFromKakao(socialToken);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);

		}
//		String social_id = Long.toString(userId);	
		System.out.println(socialId);

		try {
			isMember = userService.checkExistence(socialId);
		} catch (Exception e) {
			e.printStackTrace();
			new BusinessException("회원정보 조회 실패 ", ErrorCode.NOT_FOUND);
		}

		return ResponseEntity.ok(isMember);

	}

	/**
	 * 로그인
	 *
	 * return 1. 토큰 + 캡슐정보
	 */
	@ApiOperation(value = "로그인")
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestHeader("social-token") String socialToken) {
		String socialId;	
		String jwtToken = "";

		try {
			socialId = kakaoService.userIdFromKakao(socialToken);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);

		}

		try {
			jwtToken = userService.userLogin(socialId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("로그인에 실패하였습니다.", ErrorCode.TOKEN_ERROR);

		}
		return ResponseEntity.ok("jwtToken: " + jwtToken);

	}

	/**
	 * 회원가입
	 * 
	 * 회원가입 후 로그인까지 진행
	 */
	@ApiOperation(value = "회원가입")
	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestHeader("social-token") String socialToken, @RequestBody SignUpDto signUpDto) {
		String socialId;	
		String jwtToken;

		try {
			socialId = kakaoService.userIdFromKakao(socialToken);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);

		}

		try {
			jwtToken = userService.userSignUp(socialId, signUpDto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("회원가입에 실패하였습니다.", ErrorCode.TOKEN_ERROR);

		}
		return ResponseEntity.ok("jwtToken: " + jwtToken);

	}

	/**
	 * 닉네임 수정 중복일 시 오류 메시지 출력
	 */
	@ApiOperation(value = "닉네임 수정")
	// @ApiImplicitParam(name = "Autentication", paramType = "header", required =
	// true, value = "X-ACCESS-TOKEN")
	@PatchMapping("/nickname")
	public ResponseEntity<String> setNickname(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestBody PatchNicknameRequest nicknameDto) {

		String resultStr = "";
		long userId = 0;

		try {
			userId = jwtService.getUserId(token);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.TOKEN_ERROR);
		}

		try {
			resultStr = userService.modifyNickName(userId, nicknameDto.getNickName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.MODIFY_ERROR);
		}

		return ResponseEntity.ok(resultStr);

	}

}