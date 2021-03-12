package com.coronacapsule.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coronacapsule.api.dto.MarbleColorResultSet;
import com.coronacapsule.api.entity.Capsules;

public interface CapsuleRepository extends JpaRepository<Capsules, Long> {

	Optional<Capsules> findByUser_UserIdAndDeletedFalse(long userId);

	Optional<Capsules> findByUser_UserId(long userId);

	@Query(nativeQuery = true, 
			value="select a.marble_color marbleColor, IFNULL(b.marbleCount,0) marbleCount from  " + 
			"(select 'RED' marble_color from dual union all " + 
			"select 'YELLOW' marble_color from dual union all " + 
			"select 'BLUE' marble_color from dual union all " + 
			"select 'GREEN' marble_color from dual union all " + 
			"select 'PURPLE' marble_color from dual) a " + 
			"left join " + 
			"(select marble_color, count(*) marbleCount from marbles where capsule_id = :capsuleId group by marble_color) b " + 
			"on ( a.marble_color = b.marble_color )")
	List<MarbleColorResultSet> findMarbleColorCountsByCapsuleId(@Param("capsuleId") Long capsuleId);

}
