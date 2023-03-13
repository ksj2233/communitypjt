package com.keduit.helloworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.keduit.helloworld.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{

	
	
	/**쿠폰 값 가져오기*/
	@Query(value ="select * from coupon where serialnum = :coupon" ,nativeQuery=true)
	Coupon getCouponNum(String coupon);

	/**쿠폰 값 있나 보기 */
	@Query(value ="select Count(*) from coupon where serialnum = :coupon" ,nativeQuery=true)
	int getCountTest(String coupon);

	
}
