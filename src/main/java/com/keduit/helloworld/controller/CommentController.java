package com.keduit.helloworld.controller;

import java.util.HashMap;
import java.util.List;

import com.keduit.helloworld.repository.ViewAuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.keduit.helloworld.dto.BoardCheckLikeDTO;
import com.keduit.helloworld.dto.CommentDTO;
import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.service.BoardCheckLikeService;
import com.keduit.helloworld.service.CommentService;
import com.keduit.helloworld.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/comment/*")
public class CommentController {
	
	private final CommentService commentService;
	private final MemberService memberService;
	
	@GetMapping("/{boardNum}/all")
	public ResponseEntity<List<CommentDTO>> getListByBoard(@PathVariable("boardNum") Long boardNum){
		log.info("위치 : CommentController getListByBoard()" + boardNum);
		log.info("boardNum : " + boardNum);
		
		return new ResponseEntity<>(commentService.getList(boardNum),HttpStatus.OK);
	}
	
	@PostMapping("/{boardNum}")
	public ResponseEntity<Long> register(@RequestBody CommentDTO commentDTO, Authentication authentication){
		log.info("위치 : CommentController PostMapping register()");
		log.info("commentDTO : " + commentDTO);
		Long boardCommentNum = commentService.register(commentDTO);
		// 게시물 등록시 경험치 추가 
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		MemberDTO exe = memberService.exeModify(userDetails.getUsername());
		
		//호성 end
		
		return new ResponseEntity<Long>(boardCommentNum, HttpStatus.OK);
	}
	
	@DeleteMapping("/{boardCommentNum}")
	public ResponseEntity<String> remove(@PathVariable("boardCommentNum") Long boardCommentNum){
		log.info("위치 : CommentController remove()");
		log.info("boardCommentNum : " + boardCommentNum);
		commentService.remove(boardCommentNum);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	@PutMapping("/{boardCommentNum}")
	public ResponseEntity<String> modify(@RequestBody CommentDTO commentDTO){
		log.info("위치 : CommentController modify()");
		log.info("commentDTO : " + commentDTO);
		commentService.modify(commentDTO);
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@PostMapping("/readModal")
	public @ResponseBody CommentDTO readModal(@RequestParam HashMap<Object,Object> params){

		Long BCN = Long.parseLong(params.get("boardCommentNum").toString());

		CommentDTO commentDTO = commentService.getById(BCN);

		return commentDTO;
	}
	
	@PostMapping("/payCheck")
	public @ResponseBody boolean payCheck(@RequestParam HashMap<Object,Object> params){

		String memberNumber = params.get("memberNumber").toString();
		String boardCommentNum = params.get("boardCommentNum").toString();

		return commentService.getFindCheck(Long.parseLong(boardCommentNum),Long.parseLong(memberNumber));
	}
	
	
	
}
