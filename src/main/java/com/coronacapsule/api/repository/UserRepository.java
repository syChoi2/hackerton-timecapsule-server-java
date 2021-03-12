package com.coronacapsule.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.coronacapsule.api.dto.UserDto;
import com.coronacapsule.api.entity.Users;



public interface UserRepository extends JpaRepository<Users, Long> {


	Iterable<UserDto> findAllBySocialId(String socialId);

	
}
