package com.coronacapsule.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coronacapsule.api.entity.Capsules;

public interface CapsuleRepository extends JpaRepository<Capsules, Long> {

	Optional<Capsules> findByUser_UserIdAndDeletedFalse(long userId);

}
