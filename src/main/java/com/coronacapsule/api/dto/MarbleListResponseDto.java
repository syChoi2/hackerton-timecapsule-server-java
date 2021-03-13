package com.coronacapsule.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarbleListResponseDto {

	List<MarbleDto> marbleList;
	List<MarbleColorCount> marbleColorCount;
	
}
