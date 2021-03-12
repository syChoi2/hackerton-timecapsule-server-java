package com.coronacapsule.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coronacapsule.api.entity.Marbles;

public interface MarbleRepository extends JpaRepository<Marbles, Long> {

	Iterable<Marbles> findAllByCapsule_User_UserIdAndDeletedFalse(long userId);

	Optional<Marbles> findAllByCapsule_User_UserIdAndMarbleIdAndDeletedFalse(long userId, long marbleId);

}
