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
public class PointAccountDTO {

	/** 포인트 거래내역 고유번호 pk, nn, ai */
	private Long pointNum;
	
	/** 포인트 회원번호 fk, nn */
	private Long memberNum;
	
	/** 포인트 충전금액 */
	private Long charge;
	
	/** 포인트 잔액 */
	private Long balance;
	
	/** 포인트 환전금액 */
	private Long exchange;
	
	/** 포인트 등록날짜 */
	protected LocalDateTime regDate;
	
	/** 포인트 수정날짜 */
	protected LocalDateTime updateDate;
	
}
