package com.coronacapsule.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.dto.CapsuleDto;
import com.coronacapsule.api.dto.CapsuleNameDto;
import com.coronacapsule.api.dto.MarbleColorCount;
import com.coronacapsule.api.dto.MarbleColorResultSet;
import com.coronacapsule.api.entity.Capsules;
import com.coronacapsule.api.entity.CoronaEndFlag;
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

		if(capsuleNameDto.getCapsuleName()==null){
			throw new BusinessException("캡슐이름(capsuleName)", ErrorCode.INSUFFICIENT_VALUE);
		}
		if(capsuleNameDto.getCapsuleName().length()>10) {
			throw new BusinessException(ErrorCode.CAPSULE_NAME_TOO_LONG);
		}
		
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
		
		List<MarbleColorCount> marbleColorCountToIntList = new ArrayList<>();
		
		for (MarbleColorResultSet marbleColorResultSet : marbleColorCount) {
			MarbleColorCount mc = MarbleColorCount.builder()
					.marbleColor(marbleColorResultSet.getMarbleColor().getOrdinal())
					.marbleCount(marbleColorResultSet.getMarbleCount())
					.build();
			marbleColorCountToIntList.add(mc);
		}
		
		//Entity to Dto
 		CapsuleDto capsuleDto = capsule.convertToCapsuleDto(marbleColorCountToIntList);

		return capsuleDto;
	}


	public boolean getOpenCapsuleFlag() {
		return coronaEndFlagRepository.findById(1L).get().isFlag();
	}


	public boolean toggleFlag() {
		CoronaEndFlag flag = coronaEndFlagRepository.findById(1L).get();
		return flag.toggleFlag();
	}

}
