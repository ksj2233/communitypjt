package com.keduit.helloworld.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keduit.helloworld.dto.BoardDTO;
import com.keduit.helloworld.entity.Board;


public interface SearchBoardRepository {
	
	public Page<Object[]> searchBoard1Page(String type , String keyword , Pageable pageable);
	public Page<Object[]> searchBoard2Page(String type , String keyword , Pageable pageable);
	public Page<Object[]> searchBoard3Page(String type , String keyword , Pageable pageable);
	public Object getBoardByBno(Long boardNum); 
	
	Page<Object[]> searchPage(String type , String keyword , Pageable pageable, Long boardcase);
}
