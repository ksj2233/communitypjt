package com.keduit.helloworld.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCheckLikeDTO {

	/** 좋아요 확인 고유넘버 */
	private Long checklikeId;
	
	/** 좋아요 확인 좋아요 누른 댓글 */
	private Long commentId;
	
	/** 좋아요 확인 좋아요 누른 여부 */
	private Long memberNum;
	
}
