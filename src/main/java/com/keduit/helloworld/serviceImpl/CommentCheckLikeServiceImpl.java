package com.keduit.helloworld.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.keduit.helloworld.entity.Comment;
import com.keduit.helloworld.entity.CommentCheckLike;
import com.keduit.helloworld.repository.CommentCheckLikeRepository;
import com.keduit.helloworld.repository.CommentRepository;
import com.keduit.helloworld.service.CommentCheckLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentCheckLikeServiceImpl implements CommentCheckLikeService {

	
	private final CommentCheckLikeRepository commentCheckLikeRepository;
	
	private final CommentRepository commentRepository; 
	
	

	@Override
	public void get(Long memberNum, Long commentid) {
		
		List<CommentCheckLike> check = commentCheckLikeRepository.getbyMemberNum(memberNum,commentid);
		Optional<Comment> commentcheck = commentRepository.findById(commentid);
		log.info("위치 : CommentCheckLikeServiceImpl get()");
		log.info("commentcheck : "+commentcheck);
		
		if(!check.isEmpty()) {
			commentCheckLikeRepository.deleteById(check.get(0).getChecklikeId());
			
			if(commentcheck.isPresent()) {
				Comment comment = commentcheck.get();
				
				comment.countDown(comment.getClikes());
			
				commentRepository.save(comment);
			}
		}else {
			CommentCheckLike commentCheckLike = CommentCheckLike.builder().commentId(commentid).memberNum(memberNum).build();
			commentCheckLikeRepository.save(commentCheckLike);
			
			if(commentcheck.isPresent()) {
				
			Comment comment = commentcheck.get();
			
			comment.countUp(comment.getClikes());
			
			commentRepository.save(comment);
			}
		}
		
	}

	
}
