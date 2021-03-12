package com.coronacapsule.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.dto.CapsuleDto;
import com.coronacapsule.api.dto.CapsuleNameDto;
import com.coronacapsule.api.dto.MarbleColorResultSet;
import com.coronacapsule.api.entity.Capsules;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.repository.CapsuleRepository;
import com.coronacapsule.api.repository.CoronaEndFlagRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CapsuleService {
	

	private final CapsuleRepository capsuleRepository;
	private final CoronaEndFlagRepository coronaEndFlagRepository;
	
	
	public void setCapsuleName(long userId, CapsuleNameDto capsuleNameDto) {
		
		//유저 ID로 캡슐 검색
		Capsules capsule = capsuleRepository.findByUser_UserId(userId).orElseThrow(()-> new BusinessException("캡슐 없음", ErrorCode.NOT_FOUND));
		
		//캡슐 이름 변경
		capsule.changeCapsuleName(capsuleNameDto.getCapsuleName());
		
	}


	public CapsuleDto getCapsule(long userId) {
		
		//유저 ID로 캡슐 검색
		Capsules capsule = capsuleRepository.findByUser_UserId(userId).orElseThrow(()-> new BusinessException("캡슐 없음", ErrorCode.NOT_FOUND));

		//캡슐 ID로 색깔별 구슬 count 검색
		List<MarbleColorResultSet> marbleColorCount = capsuleRepository.findMarbleColorCountsByCapsuleId(capsule.getCapsuleId());
		
		//Entity to Dto
		CapsuleDto capsuleDto = capsule.convertToCapsuleDto(marbleColorCount);

		return capsuleDto;
	}


	public boolean getOpenCapsuleFlag() {
		return coronaEndFlagRepository.findById(1L).get().isFlag();
	}

}
