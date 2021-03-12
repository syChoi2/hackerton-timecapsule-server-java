package com.coronacapsule.api.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CapsuleDto {

	private Long capsuleId;
	
	private String capsuleName;
	private int allowedMarbleCount;
	
	private List<MarbleColorCountDto> marbleColorCount;
	
}
