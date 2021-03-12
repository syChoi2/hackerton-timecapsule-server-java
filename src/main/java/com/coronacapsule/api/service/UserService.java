package com.coronacapsule.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.coronacapsule.api.dto.*;
import com.coronacapsule.api.entity.Capsules;
import com.coronacapsule.api.entity.Users;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	
	
	public Boolean getUser(String socialId) throws Exception {
		
		UserDto users;
		boolean result =true;
	
		Iterable<UserDto> iterableUser = userRepository.findAllBySocialId(socialId);
		
		List<UserDto> userList = new ArrayList<UserDto>();
		
		for (UserDto userDto : iterableUser) {
			userList.add(userDto);
		}
		
		if(userList.size() == 0) {
			result =false;
	
		}		
	
		return result;
	

	}
	
	public String userLogin(long userId) throws Exception{
		String jwtToken="";
		try {
			jwtToken = jwtService.createJwt(userId);
		}catch(Exception e) {
			e.printStackTrace();
            throw new BusinessException("토큰 생성에 실패하였습니다.", ErrorCode.TOKEN_ERROR);
			
		}
		return jwtToken;
		
	}
}
