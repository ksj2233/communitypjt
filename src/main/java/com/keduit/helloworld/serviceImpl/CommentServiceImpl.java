package com.keduit.helloworld.serviceImpl;

import com.keduit.helloworld.dto.CommentDTO;
import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.entity.*;
import com.keduit.helloworld.entity.Comment;
import com.keduit.helloworld.repository.BoardRepository;
import com.keduit.helloworld.repository.CommentRepository;
import com.keduit.helloworld.repository.ViewAuthRepository;
import com.keduit.helloworld.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import lombok.RequiredArgsConstructor;

import java.util.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.web.jaasapi.JaasApiIntegrationFilter;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.dto.CommentDTO;
import com.keduit.helloworld.entity.Comment;
import com.keduit.helloworld.repository.CommentRepository;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;

	private final ViewAuthRepository viewAuthRepository;

	@Override
	/** 등록 */
	public Long register(CommentDTO commentDTO) {
		Comment comment = dtoToEntity(commentDTO);
		commentRepository.save(comment);
		Optional<Comment> result = commentRepository.findByCommenterNum(commentDTO.getCommenterNum());
		if(comment.getPrice() != null && comment.getPrice() != 0){
			ViewAuth viewAuth = ViewAuth
					.builder()
					.boardCommentNum(result.get().getBoardCommentNum())
					.memberNum(result.get().getCommenterNum())
					.build();
			viewAuthRepository.save(viewAuth);
		}
		return comment.getBoardCommentNum();
	}

	@Override
	public List<CommentDTO> getList(Long boardNum) {
		List<Comment> comment = commentRepository.getCommentlist(boardNum);
		List<CommentDTO> result = new ArrayList<>();

		for (Comment comm: comment) {
			if (comm.getCommenterNum() != null) {
				Optional<Member> member = memberRepository.findById(comm.getCommenterNum());
				if (member.isPresent()) {
					result.add(entityToDTO(comm, member.get()));
				}
			}
		}

		return result;
	}

	@Override
	/** 수정 */
	public void modify(CommentDTO commentDTO) {

		Comment comment = dtoToEntity(commentDTO);
		commentRepository.save(comment);
	}

	@Override
	/** 삭제 */
	public void remove(Long boardCommentNum) {

		commentRepository.deleteById(boardCommentNum);

	}

	@Override
	public boolean getFindCheck(Long BCN, Long memberNum) {

		Optional<ViewAuth> result = viewAuthRepository.findBCNAndMM(BCN,memberNum);

		if(result.isPresent()){
			return true;
		}else {
			ViewAuth viewAuth = ViewAuth
					.builder()
					.boardCommentNum(BCN)
					.memberNum(memberNum)
					.build();
			viewAuthRepository.save(viewAuth);
			return false;
		}


	}

	public CommentDTO getCommentNum(Comment comment) {

		Member member = memberRepository.getCommenter(comment.getCommenterNum());
		return entityToDTO(comment, member);
	}
	// 승민 
    @Override
    public Page<CommentDTO> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable).map(comment -> commentETD(comment));
    }

	@Override
	public Page<CommentDTO> getKeywordComments(String select,String keyword, Pageable pageable) {

		Page<CommentDTO> list = null;

		if(select.equals("board_comment_num")){
			Optional<Comment> result = commentRepository.findById(Long.parseLong(keyword));
			if(result.isPresent()){
				CommentDTO commentDTO = commentETD(result.get());
				list = new PageImpl<>(Collections.singletonList(commentDTO));
			}
		}else if(select.equals("commenter_num")){
			list = commentRepository.findByCommenterNum(keyword, pageable).map(comment -> commentETD(comment));
		}

		return list;
	}

	@Override
	public CommentDTO getById(Long BCN) {

		Optional<Comment> result = commentRepository.findById(BCN);
		if(result.isPresent()){
			Optional<Member> memNum = memberRepository.findById(result.get().getCommenterNum());
			if (memNum.isPresent()) {
			    return entityToDTO(result.get(),memNum.get());
			}
		}
		return null;
	}
	// 승민 끝

	@Override
	public List<CommentDTO> getCommentList(Long id) {

		List<Comment> comments = commentRepository.getCommentById(id);
		List<Long> boards = boardRepository.getCommentById(id);
		List<CommentDTO> commentDTOs =new ArrayList<>(); ;
		
		
//		System.out.println("보드 케이스 크기 : " + comments.size());
//		System.out.println("보드 케이스 크기 : " + boards);
		for(int i = 0; i< comments.size(); i++) {
			commentDTOs.add(CommentDTO.builder().boardCommentNum(comments.get(i).getBoardCommentNum())
										.boardNum(comments.get(i).getBoardNum())
										.commentContent(comments.get(i).getCommentContent())
										.viewpicture(comments.get(i).getViewpicture())
										.price(comments.get(i).getPrice())
										.url(comments.get(i).getUrl())
										.clikes(comments.get(i).getClikes())
										.commenterNum(comments.get(i).getCommenterNum())
										.boardcase(boards.get(i))
										.regdate(comments.get(i).getRegDate())
										.updatedate(comments.get(i).getUpdateDate())
										.build());
		}
		
//		
//		for(CommentDTO dto : commentDTOs) {
//			System.out.println(dto);
//		}
		
		return commentDTOs;
	}

	@Override
	public void boardRemove(Long boardNum) {
		
		List<Long> comment = commentRepository.findBoardCommentNum(boardNum);
		
		for(Long aa : comment) {
			commentRepository.deleteById(aa);
			
		}
		
		
	}
}
