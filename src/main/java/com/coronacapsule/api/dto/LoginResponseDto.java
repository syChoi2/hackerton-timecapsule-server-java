package com.coronacapsule.api.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class LoginResponseDto {

	private UserDto user;
	private String token;
	//private List<MarbleColorCountDto> marbleColorCountList; 
	
}
