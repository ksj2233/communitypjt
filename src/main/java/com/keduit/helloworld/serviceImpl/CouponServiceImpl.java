package com.keduit.helloworld.serviceImpl;

import java.util.List;

import com.keduit.helloworld.service.UserPageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.dto.CouponDTO;
import com.keduit.helloworld.entity.Coupon;
import com.keduit.helloworld.repository.CouponRepository;
import com.keduit.helloworld.service.CouponService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CouponServiceImpl implements CouponService{
	
	private final CouponRepository couponRepository;


	@Override
	public Page<CouponDTO> readCouponList(Pageable pageable) {
		return couponRepository.findAll(pageable).map(coupon -> EntityToDto(coupon));
	}
	//승민 끝
	
	@Override
	/** 쿠폰 번호 등록 */
	public Long register(CouponDTO dto) {
		Coupon entity = DtoToEntity(dto);
		
		couponRepository.save(entity);
		return entity.getCouponNum();
	}

	@Override
	/** 전체 쿠폰 읽어오기 */
	public List<Coupon> read() {
		
		List<Coupon> result = couponRepository.findAll();
		
		return result;
	}

	@Override
	/** 쿠폰 삭제 */
	public void remove(Long couponNum) {

		couponRepository.deleteById(couponNum);
	}

    @Service
    public static class UserPageServiceImpl implements UserPageService {
    }

    /** 쿠폰 생성*/
	@Override
	public void couponCreate() {

	String str = "0123456789qwertyuiopasdfghjklzxcvbnm";
	  for (int i = 0; i < 10; i++) {
	       Coupon coupon = Coupon
	               .builder()
	               .couponvalue(50000L)
	               .serialnum(createNum(str))
	               .build();
	       couponRepository.save(coupon);

	}
		}

		private String createNum(String str){
		    String serNum = "";
		    for (int i = 0; i < 16;i++){
		       serNum += str.indexOf((int) (Math.random() * str.length()));
		    }
		    return serNum;
 }

		@Override
		public Coupon getCouponList(String coupon) {

			Coupon coupons = couponRepository.getCouponNum(coupon);

			return coupons;
		}

		@Override
		public int getCount(String coupon) {
			int counttest = couponRepository.getCountTest(coupon);
			return counttest;
		}
}
