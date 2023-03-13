package com.keduit.helloworld.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAccountDTO {
	
	/** 회원간 거래내역 고유번호 pk, nn, ai */
	private Long accountNum;
	
	/** 회원간 거래내역 구매회원(질문자) fk,nn */
	private Long memberBuyer;
	
	/** 회원간 거래내역 판매회원(답변자) fk,nn */
	private Long memberSeller;
	
	/** 회원간 거래내역 거래금액 */
	private Long payment;
	
	/** 회원간 거래내역 등록날짜 */
	protected LocalDateTime regDate;
	
	/** 회원간 거래내역 수정날짜 */
	protected LocalDateTime updateDate;
	
}
