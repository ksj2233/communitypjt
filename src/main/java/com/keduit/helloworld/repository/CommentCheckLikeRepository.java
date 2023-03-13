package com.keduit.helloworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.keduit.helloworld.entity.CommentCheckLike;

public interface CommentCheckLikeRepository extends JpaRepository<CommentCheckLike, Long>{

	
	
	
	@Query(value = " select * from comment_check_like where (member_num = :memberNum and comment_id = :commentId) ",nativeQuery = true)
	List<CommentCheckLike> getbyMemberNum(Long memberNum, Long commentId);
	

	
}
