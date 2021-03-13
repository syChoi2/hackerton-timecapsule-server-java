package com.coronacapsule.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MarbleColorCount {

	int marbleColor;
	int marbleCount;
}
