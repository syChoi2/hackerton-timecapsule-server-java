package com.coronacapsule.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarbleDto {

	private Long marbleId;
	
	private String content;
	private String marbleColor;
	private boolean wishChecked;
	
}
