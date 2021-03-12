package com.coronacapsule.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.dto.MarbleDto;
import com.coronacapsule.api.dto.PostMarbleRequestDto;
import com.coronacapsule.api.entity.Capsules;
import com.coronacapsule.api.entity.Marbles;
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

	public List<MarbleDto> getMarbleList(long userId) {
		
		Iterable<MarbleDto> iterableMarble = marbleRepository.findAllByCapsule_User_UserIdAndDeletedFalse(userId);
		
		List<MarbleDto> marbleList = new ArrayList<MarbleDto>();
		
		for (MarbleDto marbleDto : iterableMarble) {
			marbleList.add(marbleDto);
		}
		
		return marbleList;
	}

}
