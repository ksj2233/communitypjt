package com.keduit.helloworld.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.BoardCheckLike;
import com.keduit.helloworld.repository.BoardCheckLikeRepository;
import com.keduit.helloworld.repository.BoardRepository;
import com.keduit.helloworld.service.BoardCheckLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardCheckLikeServiceImpl implements BoardCheckLikeService {

	
	private final BoardCheckLikeRepository boardCheckLikeRepository;
	
	private final BoardRepository boardRepository; 
	
	
	@Override
	public void get(Long memberNum, Long boardNum) {
		
		List<BoardCheckLike> check = boardCheckLikeRepository.getbyMemberNum(memberNum,boardNum);
		Optional<Board> boardcheck = boardRepository.findById(boardNum);
		
		
		if(!check.isEmpty()) {
			boardCheckLikeRepository.deleteById(check.get(0).getChecklikeId());
			
			if(boardcheck.isPresent()) {
				Board board = boardcheck.get();
				
				board.countDown(board.getBlikes());
			
			boardRepository.save(board);
			}
		}else {
			BoardCheckLike boardCheckLike = BoardCheckLike.builder().boardNum(boardNum).memberNum(memberNum).build();
			boardCheckLikeRepository.save(boardCheckLike);
			
			if(boardcheck.isPresent()) {
				
			Board board = boardcheck.get();
			
			board.countUp(board.getBlikes());
			
			boardRepository.save(board);
			}
		}
		
	}

	
}
