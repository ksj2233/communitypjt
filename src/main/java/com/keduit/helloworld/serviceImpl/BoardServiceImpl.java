package com.keduit.helloworld.serviceImpl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.dto.BoardDTO;
import com.keduit.helloworld.dto.PageRequestDTO;
import com.keduit.helloworld.dto.PageResultDTO;
import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.BoardRepository;
import com.keduit.helloworld.repository.CommentRepository;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;
	
	@Override
	/** 생성 */
	public Long register(BoardDTO boardDTO) {
		
		Board board = dtoToEntity(boardDTO);
		
		boardRepository.save(board);
		
		return board.getBoardNum();
	}

	@Override
	/** 읽기 */
	public PageResultDTO<BoardDTO, Object[]> getBoard1List(PageRequestDTO pageRequestDTO) {
		
		log.info("위치 : BoardServiceImpl getBoard1List() ");
		log.info("pageRequestDTO : "+ pageRequestDTO);
		
		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
		
		Page<Object[]> result = boardRepository.searchBoard1Page(
				pageRequestDTO.getType(),
				pageRequestDTO.getKeyword(),
				pageRequestDTO.getPageable(Sort.by("boardNum").descending())
				);
		
		return new PageResultDTO<>(result, fn);
	}
	
	
	@Override
	/** 읽기 */
	public PageResultDTO<BoardDTO, Object[]> getBoard2List(PageRequestDTO pageRequestDTO) {
		
		log.info("위치 : BoardServiceImpl getBoard2List()"+pageRequestDTO);
		log.info("pageRequestDTO : "+pageRequestDTO);
		
		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
		
		Page<Object[]> result = boardRepository.searchBoard2Page(
				pageRequestDTO.getType(),
				pageRequestDTO.getKeyword(),
				pageRequestDTO.getPageable(Sort.by("boardNum").descending())
				);
		
		return new PageResultDTO<>(result, fn);
	}
	
	
	@Override
	/** 읽기 */
	public PageResultDTO<BoardDTO, Object[]> getBoard3List(PageRequestDTO pageRequestDTO) {
		
		log.info("pageRequestDTO : "+pageRequestDTO);
		
		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
		
		Page<Object[]> result = boardRepository.searchBoard3Page(
				pageRequestDTO.getType(),
				pageRequestDTO.getKeyword(),
				pageRequestDTO.getPageable(Sort.by("boardNum").descending())
				);
		
		return new PageResultDTO<>(result, fn);
	}
	

	@Override
	/** 삭제 */
	public void remove(Long boardNum) {
		boardRepository.deleteById(boardNum);
	}

	@Override
	/** 수정 */
	public void modify(BoardDTO boardDTO) {
		Optional<Board> result = boardRepository.findById(boardDTO.getBoardNum());
		
		if(result.isPresent()) {
			Board entity = result.get();
			
			entity.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getTag());
			boardRepository.save(entity);
		}
	}

	@Override
	/** 하나 읽기 */
	public BoardDTO get(Long boardNum) {
		Board boardResult = boardRepository.getBoardByBno(boardNum);
		Member memberResult = memberRepository.getBoardByBno(boardNum);
		Long commentResult = commentRepository.getBoardByBno(boardNum);

		return entityToDTO(boardResult, memberResult, commentResult);
	}

	@Override
	@Transactional
	public void updateViews(Long boardNum, BoardDTO boardDTO) {
		Board board = boardRepository.findById(boardNum).orElseThrow((()->
		new IllegalStateException("해당 게시글이 존재하지 않습니다.")));

		board.updateViews(boardDTO.getViews());
	}


	@Override
	public List<Board> getMyBoardList(String id) {

		List<Board> list = boardRepository.getMyBoardList(id);

		return list;
	}

	//승민
	@Override
	public Page<BoardDTO> getBoards(Pageable pageable) {
		return boardRepository.findAll(pageable).map(board -> boardETD(board));
	}

	@Override
	public Page<BoardDTO> getKeywordBoards(String select,String keyword, Pageable pageable) {

		Page<BoardDTO> list = null;

		if(select.equals("board_num")){
			Optional<Board> result = boardRepository.findById(Long.parseLong(keyword));
			if(result.isPresent()){
				BoardDTO boardDTO = boardETD(result.get());
				list = new PageImpl<>(Collections.singletonList(boardDTO));
			}
		}else if(select.equals("member_num")){
			list = boardRepository.findByTitle(keyword, pageable).map(board -> boardETD(board));
		}else if(select.equals("title")){
			list = boardRepository.findByMemberNum(keyword, pageable).map(board -> boardETD(board));
		}

		return list;
	}
	//승민 끝

	//호성 top Community 제작 23.02.22
	/** 커뮤니티 조회수 순서대로 가져오기 */
	@Override
	public List<Board> topboard() {
		List<Board> dto = boardRepository.getBoardCount();
	
		
		return dto;
	}

	//호성 end
}
