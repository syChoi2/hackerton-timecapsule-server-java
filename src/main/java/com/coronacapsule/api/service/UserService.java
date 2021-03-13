package com.coronacapsule.api.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.dto.SignUpDto;
import com.coronacapsule.api.entity.Capsules;
import com.coronacapsule.api.entity.Users;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.repository.CapsuleRepository;
import com.coronacapsule.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	private final CapsuleRepository capsuleRepository;
	private final JwtService jwtService;
	
	
	public Boolean checkExistence(String socialId) throws Exception {
		
		return userRepository.existsBySocialIdAndDeletedFalse(socialId);

	}
	
	public String userLogin(String social_id) throws Exception{
		
		// 1. 사용자 존재여부 확인
		Users user = userRepository.findBySocialId(social_id).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));
		
		// 2. 토큰 발급	socialId가 아니라 userId 로 해야함	//만약 social id도 token에 담고 싶으면 추가해도 되지만 지금은 사용할 데가 없으니 userId만 담겠습니다.
		String jwtToken = createJwtToken(user.getUserId());
		
		return jwtToken;
		
	}
	
	public String userSignUp(String socialId, SignUpDto param) throws Exception{
		
		//user 생성
		String nickName = param.getNickname();
		
		Users newUser = Users.builder()
				.socialId(socialId)
				.nickname(nickName)
				.build();
		
		newUser = userRepository.save(newUser);
		
		//capsule 생성
		Capsules capsule = Capsules.builder()
				.build();
		
		capsule.setUsers(newUser);
		
		capsuleRepository.save(capsule);

//		Long jwtLongToken = Long.parseLong(socialId);
//		String jwtToken= createJwtToken(jwtLongToken);	//socialId가 아니라 userId 로 해야함	//만약 social id도 token에 담고 싶으면 추가해도 되지만 지금은 사용할 데가 없으니 userId만 담겠습니다.
		String jwtToken= createJwtToken(newUser.getUserId());
		
		return jwtToken;
	}
	
	
	public String modifyNickName(long userId, String nickName) throws Exception{
		
		Optional<Users> optionalUser;
		Users user;
		String resultStr = "";
		
		try {
			optionalUser = userRepository.findById(userId);	
		}catch(Exception e) {
			e.printStackTrace();
            throw new BusinessException( ErrorCode.USER_NOT_FOUND);

		}
		
		//위에서 처리함
//		if(userList.size() == 0) {
//            throw new BusinessException("가입되어 있는 회원이 아닙니다", ErrorCode.USER_NOT_FOUND);
//
//		}
		
		user = optionalUser.get();
		
		user.changeNickname(nickName);
//		Users modifyUser = Users.builder()
//				.socialId(user.getSocialId())		
//				.build();
		
		resultStr =  "(" + userId + ")님의 닉네임이 " +nickName + "으로 변경되었습니다.";
		
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
