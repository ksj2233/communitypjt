package com.keduit.helloworld.service;

import java.util.List;

import com.keduit.helloworld.dto.CouponDTO;
import com.keduit.helloworld.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {

	Page<CouponDTO> readCouponList(Pageable pageable);
	// 승민 끝
	/** 쿠본 번호 등록*/
	Long register(CouponDTO dto);
	
	/**전체 쿠폰 읽어오기*/
	List<Coupon> read();
	
	/** 쿠폰 삭제*/
	void remove(Long couponNum);
	
	/**쿠폰 entity -> 쿠폰 DTO*/
	default CouponDTO EntityToDto(Coupon entity) {
		
		CouponDTO dto = CouponDTO.builder().couponNum(entity.getCouponNum())
											.couponvalue(entity.getCouponvalue())
											.serialnum(entity.getSerialnum())
											.couponbool(entity.getCouponbool())
											.build();
		return dto;
	}
	
	/**쿠폰DTO -> 쿠폰Entity*/
	default Coupon DtoToEntity(CouponDTO dto) {
		
		Coupon entity = Coupon.builder().couponNum(dto.getCouponNum())
										.couponvalue(dto.getCouponvalue())
										.serialnum(dto.getSerialnum())
										.couponbool(dto.getCouponbool())
										.build();
		return entity;
	}

	
	
	/** 쿠폰 생성 */
	void couponCreate();

	/**쿠폰 번호 출력하기 */
	Coupon getCouponList(String coupon);

	/** 쿠폰 번호가 있는지 ? */
	int getCount(String coupon);
	
}
