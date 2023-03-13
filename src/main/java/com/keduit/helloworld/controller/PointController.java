package com.keduit.helloworld.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.PointAccountDTO;
import com.keduit.helloworld.entity.Coupon;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.service.CouponService;
import com.keduit.helloworld.service.MemberService;
import com.keduit.helloworld.service.PointAccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/hello/*")
public class PointController {
	
	private final MemberService memberService;
	private final PointAccountService pointAccountService;
	private final CouponService couponService;
	
//호성 23.02.21
	@GetMapping("/point")
	public void message(Authentication authentication, Model model) {
		log.info("=============== PointController ===============");
		//내 아이디 가져오기
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Member me =  memberService.idRead(userDetails.getUsername());
		// 내 거래내역 가져오기
		List<PointAccountDTO> pointDto = pointAccountService.read(me.getMemberNum());
		
		model.addAttribute("my", me);
		model.addAttribute("list",pointDto);
	}
	
	@GetMapping("/charge")
	public void charge() {	
	}
	
	@PostMapping("/charge")
	public String charge1(Long id, Long point, PointAccountDTO dto) {
		// 내 정보 dto에 담아두기
		MemberDTO memdto = memberService.read(id);
		// 내 거래내역에 추가하기
		pointAccountService.setCharge(id, point, dto.getCharge(), memdto);
		
		
		return "redirect:/hello/point";
	}
	
	@GetMapping("/exchange")
	public void exchange() {
		
	}

	
	@PostMapping("/exchange")
	public String exchange1(Long id, Long point, PointAccountDTO dto) {
		// 내 정보 dto에 담아두기
		MemberDTO memdto = memberService.read(id);
		// 내 거래내역에 추가하기
		pointAccountService.setExCharge(id, point, dto.getExchange(), memdto);
		
		
		return "redirect:/hello/point";
	}
	
	/** 쿠폰 번호 비교 */
	@PostMapping("/coupon/chk/{coupon}")
	public ResponseEntity<Integer> chk(@PathVariable("coupon") String coupon,Authentication authentication){
		int i = 1;
		System.out.println("쿠폰번호가 잘 찍히나?"+coupon);
		//내 아이디 가져오기
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Member me =  memberService.idRead(userDetails.getUsername());
		
		//내가 쓴 쿠폰 번호가 있는 번호면 카운트 1 없으면 카운트 0
		int getTest = couponService.getCount(coupon);
		
		if(getTest != 0) {
			Coupon coupons = couponService.getCouponList(coupon);
			me.CopounPoint(coupons.getCouponvalue());
			memberService.couponadd(me);
			couponService.remove(coupons.getCouponNum());
			
			i= 0;

		}
		
		
		return new ResponseEntity<>(i , HttpStatus.OK);
	}
//호성 end

	
//승민
	@PostMapping("/transaction")
	/** qna 댓글에서 회원간 포인트 거래 발생 시 */
	public @ResponseBody String transaction(@RequestParam HashMap<Object,Object> params){
		String myNum = params.get("myNum").toString();
		String yourNum = params.get("yourNum").toString();
		String point = params.get("point").toString();

		pointAccountService.modify(Long.parseLong(myNum),Long.parseLong(yourNum),Long.parseLong(point));

		return "success";
	}
//승민 끝

	
//효영
	@GetMapping("/kakaopay") //결제 페이지로 이동하는 매핑
	public void kakaoPay(Authentication authentication, Model model) {
		
		//내 아이디 가져오기
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		MemberDTO memberDTO = memberService.getMyInfo(userDetails.getUsername());
		
		model.addAttribute("myInfo", memberDTO);
	}

	@PostMapping("/kakaopay") //실제 결제용 페이지
	public @ResponseBody boolean chargePoint(@RequestBody HashMap<Object, Object> params) { //charge=충전금액
		
		log.info(params);
		
		String memberNum = params.get("memberNum").toString();	
		String charge = params.get("charge").toString();	
		
		log.info(memberNum + charge);
		
		boolean success = pointAccountService.chargePoint(Long.parseLong(memberNum), Long.parseLong(charge));
		
		return success; 
	}
//효영 끝
	
}
