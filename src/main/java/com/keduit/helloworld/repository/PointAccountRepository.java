package com.keduit.helloworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.keduit.helloworld.entity.PointAccount;

@Repository
public interface PointAccountRepository extends JpaRepository<PointAccount, Long> {

	@Query(value = "select * from point_account "
			+ "where member_num = :memberNum order by updatedate desc", nativeQuery = true)
	/** 포인트 거래내역 리스트 조회(read) */
	List<PointAccount> getPointAccount(Long memberNum);

}
