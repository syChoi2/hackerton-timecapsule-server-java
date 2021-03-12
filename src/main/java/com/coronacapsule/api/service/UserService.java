package com.coronacapsule.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.dto.UserDto;
import com.coronacapsule.api.entity.Users;
import com.coronacapsule.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	
	
	public Boolean getUser(String socialId) throws Exception {
		
		UserDto users;
		boolean result =true;
	
		Iterable<UserDto> iterableUser = userRepository.findAllBySocialId(socialId);
		
		List<UserDto> userList = new ArrayList<UserDto>();
		
		for (UserDto userDto : iterableUser) {
			userList.add(userDto);
		}
		
		if(userList.size() == 0) {
			
//	        throw new BusinessException("사용자 없음", ErrorCode.NOT_FOUND);
			result =false;
	
		}
//		users = userList.get(0);
		
	
		return result;
	
		

	}

    public Optional<Users> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
