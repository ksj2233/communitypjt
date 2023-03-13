package com.keduit.helloworld.service;

import java.util.List;

import com.keduit.helloworld.dto.TetrisDTO;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.entity.Tetris;

public interface TetrisService {

	/** 점수 등록 */
	void createRank(Long memberNum, Long score);

	/** 상위 일단 3명 출력*/
	List<TetrisDTO> getMax3();

	default TetrisDTO tetrisETD(Tetris tetris, Member member){
		TetrisDTO tetrisDTO = TetrisDTO
				.builder()
				.tno(tetris.getTno())
				.score(tetris.getScore())
				.memberNum(tetris.getMemberNum())
				.url(member.getUrl())
				.nickName(member.getNickname())
				.build();
		return tetrisDTO;
	}

	default Tetris tetrisDTE(TetrisDTO tetrisDTO){
		Tetris tetris = Tetris
				.builder()
				.tno(tetrisDTO.getTno())
				.score(tetrisDTO.getScore())
				.memberNum(tetrisDTO.getMemberNum())
				.build();
		return tetris;
	}

}
