package com.coronacapsule.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarbleDto {

	private Long marbleId;
	
	private String content;
	private int marbleColor;
	private boolean wishChecked;
	private LocalDate createdAt;	
}
