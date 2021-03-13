package com.coronacapsule.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coronacapsule.api.dto.MarbleColorResultSet;
import com.coronacapsule.api.entity.Marbles;
import com.coronacapsule.api.enums.MarbleColor;

public interface MarbleRepository extends JpaRepository<Marbles, Long> {

	Iterable<Marbles> findAllByCapsule_User_UserIdAndDeletedFalseOrderByMarbleId(long userId);

	Optional<Marbles> findAllByCapsule_User_UserIdAndMarbleIdAndDeletedFalse(long userId, long marbleId);

	@Query(nativeQuery = true, 
			value="select a.marble_color marbleColor, IFNULL(b.marbleCount,0) marbleCount from  " + 
					"(select 'RED' marble_color from dual union all " + 
					"select 'YELLOW' marble_color from dual union all " + 
					"select 'BLUE' marble_color from dual union all " + 
					"select 'GREEN' marble_color from dual union all " + 
					"select 'PURPLE' marble_color from dual) a " + 
					"left join " + 
					"(select marble_color, count(*) marbleCount from marbles where capsule_id in (select id from capsules where user_id = :userId) and wish_checked = false group by marble_color) b " +
					"on ( a.marble_color = b.marble_color )")
	List<MarbleColorResultSet> findMarbleColorCountsByUserId(@Param("userId") long userId);

	Iterable<Marbles> findAllByCapsule_User_UserIdAndMarbleColorAndDeletedFalseOrderByMarbleId(long userId, MarbleColor marbleColor);

	
	Iterable<Marbles> findAllByCapsule_User_UserIdAndWishCheckedTrueAndDeletedFalseOrderByModifiedAtDesc(long userId);

}
