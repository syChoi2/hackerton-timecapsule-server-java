package com.coronacapsule.api.dto;

import com.coronacapsule.api.enums.MarbleColor;

import lombok.Getter;

@Getter
public class MarbleColorCountDto {

	private MarbleColor marbleColor;
	private int count;
}
