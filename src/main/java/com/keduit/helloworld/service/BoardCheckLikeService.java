package com.keduit.helloworld.service;

import com.keduit.helloworld.dto.BoardCheckLikeDTO;
import com.keduit.helloworld.entity.BoardCheckLike;
import com.keduit.helloworld.entity.Member;

public interface BoardCheckLikeService {

	/** 읽기 */
	void get(Long memberNum, Long boardNum);

	default BoardCheckLike dtoToEntity(BoardCheckLikeDTO dto) {
		

		BoardCheckLike boardCheckLike = BoardCheckLike.builder()
							.checklikeId(dto.getChecklikeId())
							.boardNum(dto.getBoardNum())
							.memberNum(dto.getMemberNum())
							.build();
		
		return boardCheckLike;
	}
	
	
	default BoardCheckLikeDTO entityToDTO(BoardCheckLike boardCheckLike) {
		

		BoardCheckLikeDTO boardCheckLikeDTO = BoardCheckLikeDTO.builder()
							.boardNum(boardCheckLike.getBoardNum())
							.memberNum(boardCheckLike.getMemberNum())
							.checklikeId(boardCheckLike.getChecklikeId())
							.build();
		
		return boardCheckLikeDTO;
	}

}
