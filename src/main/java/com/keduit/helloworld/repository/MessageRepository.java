package com.keduit.helloworld.repository;

import java.util.List;
import java.util.Optional;

import com.keduit.helloworld.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.keduit.helloworld.entity.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	//쪽지 내역 조회	
	@Query(value = "select * from message ms "
			+ "join member m on ms.member_give = m.member_num "
			+ "where ms.member_get = :memGetNum "
			+ "and view in (0, 1) order by ms.regdate desc", nativeQuery = true)
	/** 받은사람 회원번호로, 받은쪽지 정보 & 보낸회원 정보 조회 (수신 list, 권한 0 or 1만 출력) */
	List<Message> getMsgInfoAsGetter(Long memGetNum);
	
	@Query(value = "select * from message ms "
			+ "join member m on ms.member_get = m.member_num "
			+ "where ms.member_give = :memGiveNum "
			+ "and view in (0, 2) order by ms.regdate desc", nativeQuery = true)
	/** 보낸사람 회원번호로, 보낸쪽지 정보 & 받는회원 정보 조회 (발신 list, 권한 0 or 2만 출력) */
	List<Message> getMsgInfoAsGiver(Long memGiveNum);

	//승민
	@Query(value = "SELECT * FROM message WHERE (member_give = :str1 and member_get = :str2 and boardCommentNum = :str3)", nativeQuery = true)
	Page<Message> findByGiveGetBCN(String str1, String str2, String str3, Pageable pageable);
	
	//승민 끝

}
