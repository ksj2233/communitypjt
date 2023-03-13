package com.keduit.helloworld.service;

import com.keduit.helloworld.dto.CommentCheckLikeDTO;
import com.keduit.helloworld.entity.CommentCheckLike;

public interface CommentCheckLikeService {

	/** 읽기 */
	void get(Long memberNum, Long commentid);

	default CommentCheckLike dtoToEntity(CommentCheckLikeDTO dto) {
		

		CommentCheckLike commentCheckLike = CommentCheckLike.builder()
							.checklikeId(dto.getChecklikeId())
							.commentId(dto.getCommentId())
							.memberNum(dto.getMemberNum())
							.build();
		
		return commentCheckLike;
	}
	
	
	default CommentCheckLikeDTO entityToDTO(CommentCheckLike commetnCheckLike) {
		

		CommentCheckLikeDTO commentCheckLikeDTO = CommentCheckLikeDTO.builder()
							.checklikeId(commetnCheckLike.getChecklikeId())
							.memberNum(commetnCheckLike.getMemberNum())
							.checklikeId(commetnCheckLike.getChecklikeId())
							.build();
		
		return commentCheckLikeDTO;
	}

}
