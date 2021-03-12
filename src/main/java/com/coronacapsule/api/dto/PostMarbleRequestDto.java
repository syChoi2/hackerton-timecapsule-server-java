package com.coronacapsule.api.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.coronacapsule.api.enums.MarbleColor;

import lombok.Getter;

@Getter
public class PostMarbleRequestDto {

	private String content;
	@Enumerated(EnumType.ORDINAL)
	private MarbleColor marbleColor;
	
}
