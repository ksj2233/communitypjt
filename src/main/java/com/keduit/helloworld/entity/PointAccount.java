package com.keduit.helloworld.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PointAccount extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** 포인트 거래내역 고유번호 pk, nn, ai */
	private Long pointNum;
	
	@Column(nullable = false) 
	/** 포인트 회원번호  fk, nn */
	private Long memberNum; //회원번호(멤버):거래내역 = 1:n
	
	@Column
	/** 포인트 충전금액 */
	private Long charge;
	
	@Column(nullable = false)
	/** 포인트 잔액 nn */
	private Long balance;
	
	@Column
	/** 포인트 환전금액 */
	private Long exchange;

	
}
