package com.coronacapsule.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.dto.MarbleColorResultSet;
import com.coronacapsule.api.dto.MarbleDto;
import com.coronacapsule.api.dto.MarbleListResponseDto;
import com.coronacapsule.api.dto.PostMarbleRequestDto;
import com.coronacapsule.api.entity.Capsules;
import com.coronacapsule.api.entity.Marbles;
import com.coronacapsule.api.enums.MarbleColor;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.repository.CapsuleRepository;
import com.coronacapsule.api.repository.MarbleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarbleService {

	private final MarbleRepository marbleRepository;
	private final CapsuleRepository capsuleRepository;
	
	public void putMarble(long userId, PostMarbleRequestDto marble) {
	
		if(marble.getContent().length()>21) {
			throw new BusinessException(ErrorCode.MARBLE_CONTENT_TOO_LONG);
		}
		Capsules capsule = capsuleRepository.findByUser_UserIdAndDeletedFalse(userId).orElseThrow(() -> new BusinessException("캡슐 정보 없음", ErrorCode.NOT_FOUND));

		if(capsule.getAllowedMarbleCount() - capsule.getMarbleList().size() <= 0) {
			throw new BusinessException(ErrorCode.NO_EMPTY_SPACE);
		}
		
		Marbles newMarble = Marbles.builder()
				.content(marble.getContent())
				.marbleColor(marble.getMarbleColor())
				.wishChecked(false)
				.build();
		newMarble.setCapsule(capsule);
		
		marbleRepository.save(newMarble);
		
	}

	public MarbleListResponseDto getMarbleList(long userId, MarbleColor marbleColor) {
		Iterable<Marbles> iterableMarble;
		if(marbleColor != null ) {
			iterableMarble = marbleRepository.findAllByCapsule_User_UserIdAndMarbleColorAndDeletedFalseOrderByMarbleId(userId, marbleColor);
		}else {
			iterableMarble = marbleRepository.findAllByCapsule_User_UserIdAndDeletedFalseOrderByMarbleId(userId);
		}
		
		List<MarbleDto> marbleList = new ArrayList<MarbleDto>();
		
		for (Marbles marble : iterableMarble) {
			marbleList.add(marble.convertToDto());
		}
		
		//캡슐 ID로 색깔별 구슬 count 검색
		List<MarbleColorResultSet> marbleColorCount = marbleRepository.findMarbleColorCountsByUserId(userId);
		
		MarbleListResponseDto marbleListResponseDtoList = MarbleListResponseDto.builder()
				.marbleList(marbleList)
				.marbleColorCount(marbleColorCount)
				.build();
		
		return marbleListResponseDtoList;
	}

	public void checkWish(long userId, long marbleId) {
		

		Marbles marble = marbleRepository.findAllByCapsule_User_UserIdAndMarbleIdAndDeletedFalse(userId, marbleId).orElseThrow(() -> new BusinessException("운석 정보 없음", ErrorCode.NOT_FOUND));
		
		marble.checkWish();
		
	}

	public List<MarbleDto> getChekcedMarbleList(long userId) {
		Iterable<Marbles> iterableMarble = marbleRepository.findAllByCapsule_User_UserIdAndWishCheckedTrueAndDeletedFalseOrderByModifiedAtDesc(userId);
	
		List<MarbleDto> marbleList = new ArrayList<MarbleDto>();
		
		for (Marbles marble : iterableMarble) {
			marbleList.add(marble.convertToDto());
		}
		return marbleList;
	}

}
