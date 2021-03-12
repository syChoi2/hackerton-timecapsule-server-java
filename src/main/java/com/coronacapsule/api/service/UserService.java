package com.coronacapsule.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
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
		
		String jwtToken= createJwtToken(userId);
		
		return jwtToken;
		
	}
	
	public String userSignUp(String socialId, SignUpDto param) throws Exception{
		
		Long jwtLongToken = Long.parseLong(socialId);
		String jwtToken= createJwtToken(jwtLongToken);
		String nickName = param.getNickname();
		
		Users newUser = Users.builder()
				.socialId(socialId)
				.nickname(nickName)
				.build();
		
		userRepository.save(newUser);
		
		return jwtToken;
	}
	
	
	public String modifyNickName(long userId, String nickName) throws Exception{
		
		List<PatchUserDto> userList;
		PatchUserDto user;
		String resultStr = "";
		
		try {
			userList = userRepository.findAllByUserId(userId);
		}catch(Exception e) {
			e.printStackTrace();
            throw new BusinessException( ErrorCode.USER_NOT_FOUND);

		}
		
		if(userList.size() == 0) {
            throw new BusinessException("가입되어 있는 회원이 아닙니다", ErrorCode.USER_NOT_FOUND);

		}
		user = userList.get(0);
		
		
		Users modifyUser = Users.builder()
				.socialId(user.getSocialId())		
				.build();
		
		resultStr = user.getNickName() + "(" + userId + ")님의 닉네임이 " +nickName + "으로 변경되었습니다.";
		
		return resultStr;
	}
	
	
	/**
	 * token 발급 재사용성을 위한 
	 */
	public String createJwtToken(long userId) throws Exception{
		String jwtToken="";
		try {
			jwtToken = jwtService.createJwt(userId);
		}catch(Exception e) {
			e.printStackTrace();
            throw new BusinessException("토큰 생성에 실패하였습니다.", ErrorCode.TOKEN_ERROR);
			
		}
		return jwtToken;
	}
	
	  public Optional<Users> findById(Long userId) {
	        return userRepository.findById(userId);
	    }
}
