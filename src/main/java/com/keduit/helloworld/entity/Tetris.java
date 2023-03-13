package com.keduit.helloworld.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Tetris{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tno;
	@Column
	private Long memberNum;
	@Column
	private Long score;
	
}
