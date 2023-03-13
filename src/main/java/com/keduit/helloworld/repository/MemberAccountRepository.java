package com.keduit.helloworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.entity.MemberAccount;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long>{
	
	@Query(value = "select * from member_account ma "
			+ "join member m on ma.member_seller = m.member_num "
			+ "where ma.member_buyer = :num", nativeQuery = true)
	/** 회원간 거래내역 리스트 조회(read, 구매자=질문자 기준) */
	List<MemberAccount> getPayListAsBuyer(Long num);
	
	@Query(value = "select * from member_account ma "
			+ "join member m on ma.member_buyer = m.member_num "
			+ "where ma.member_seller = :num", nativeQuery = true)
	/** 회원간 거래내역 리스트 조회(read, 판매자=답변자 기준) */
	List<MemberAccount> getPayListAsSeller(Long num);
	
	
}
