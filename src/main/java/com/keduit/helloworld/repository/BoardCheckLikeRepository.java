package com.keduit.helloworld.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.keduit.helloworld.entity.BoardCheckLike;

public interface BoardCheckLikeRepository extends JpaRepository<BoardCheckLike, Long>{

	
	
	
	@Query(value = " select * from board_check_like where (member_num = :memberNum and board_num = :boardNum) ",nativeQuery = true)
	List<BoardCheckLike> getbyMemberNum(Long memberNum, Long boardNum);
	

	
}
