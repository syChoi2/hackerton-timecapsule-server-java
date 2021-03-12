package com.coronacapsule.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coronacapsule.api.dto.MarbleDto;
import com.coronacapsule.api.entity.Marbles;

public interface MarbleRepository extends JpaRepository<Marbles, Long> {

	Iterable<MarbleDto> findAllByCapsule_User_UserIdAndDeletedFalse(long userId);

}
