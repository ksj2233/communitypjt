package com.keduit.helloworld.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** Coupon 고유번호 pk,nn,ai */
	private Long couponNum;
	
	@ColumnDefault("0")
	/** Coupon 쿠폰금액 */
	private Long couponvalue;
	
	@Column(length = 20, unique = true, nullable = false)
	/** Coupon 쿠폰번호(사용되는 쿠폰의 번호) */
	private String serialnum;
	
	@ColumnDefault("0")
	/** Coupon 0사용가능 1사용불가 */
	private Long couponbool;
	

	
}
