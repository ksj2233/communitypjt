package com.keduit.helloworld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewAuthDTO {

	/** 보기권한 고유번호 pk */
	private Long viewMemberNum; //보기권한번호
	
	/** 보기권한 회원번호 */
	private Long boardCommentNum; //보기권한 회원번호

	/** 보기권한 볼수있는 아이디 */
	private String viewid; //보기권한 아이디
	
}
