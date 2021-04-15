package com.coronacapsule.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coronacapsule.api.dto.PatchUserDto;
import com.coronacapsule.api.entity.Users;


public interface UserRepository extends JpaRepository<Users, Long> {


	
	List<PatchUserDto> findAllByUserId(long id);


	boolean existsBySocialIdAndDeletedFalse(String socialId);


	Optional<Users> findBySocialIdAndDeleted(String social_id, boolean isDeleted);


	
}

