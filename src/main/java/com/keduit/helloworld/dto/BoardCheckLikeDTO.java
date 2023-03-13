package com.keduit.helloworld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardCheckLikeDTO {

	/** 좋아요 확인 고유넘버 */
	private Long checklikeId;
	
	/** 좋아요 확인 좋아요 누른 댓글 */
	private Long boardNum;
	
	/** 좋아요 확인 좋아요 누른 여부 */
	private Long memberNum;
	
}
