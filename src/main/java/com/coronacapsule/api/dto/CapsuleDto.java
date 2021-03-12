package com.coronacapsule.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapsuleDto {

	private Long capsuleId;
	
	private String capsuleName;
	private int allowedMarbleCount;
	
	private List<MarbleColorResultSet> marbleColorCount;
	
}
