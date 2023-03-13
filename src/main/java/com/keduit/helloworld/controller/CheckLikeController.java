package com.keduit.helloworld.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keduit.helloworld.dto.BoardCheckLikeDTO;
import com.keduit.helloworld.dto.CommentCheckLikeDTO;
import com.keduit.helloworld.service.BoardCheckLikeService;
import com.keduit.helloworld.service.CommentCheckLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/like/*")
public class CheckLikeController {
	

	
	private final BoardCheckLikeService boardCheckLikeService;
	private final CommentCheckLikeService commentCheckLikeService;
	
	@PostMapping("/{boardNum}")
	public ResponseEntity<Long> boardLike(@RequestBody BoardCheckLikeDTO boardCheckLikeDTO){
		log.info("위치 : CheckLikeController boardLike()");
		log.info("boardCheckLikeDTO : " + boardCheckLikeDTO);
		log.info("나오냐 " + boardCheckLikeDTO.getMemberNum() + boardCheckLikeDTO.getBoardNum());
		
		boardCheckLikeService.get(boardCheckLikeDTO.getMemberNum(), boardCheckLikeDTO.getBoardNum());
		return new ResponseEntity<Long>(1L, HttpStatus.OK);
	}
	
	@PostMapping("/{commentId}/all")
	public ResponseEntity<Long> commentLike(@PathVariable("commentId") Long commentId,@RequestBody CommentCheckLikeDTO commentCheckLikeDTO){
		log.info("위치 : CheckLikeController commentLike()");
		log.info("boardCheckLikeDTO : " + commentCheckLikeDTO);
		log.info("나오냐 " + commentCheckLikeDTO.getMemberNum() + commentCheckLikeDTO.getCommentId());
		
		commentCheckLikeService.get(commentCheckLikeDTO.getMemberNum(), commentId);
		return new ResponseEntity<Long>(1L, HttpStatus.OK);
	}
	
	
}
