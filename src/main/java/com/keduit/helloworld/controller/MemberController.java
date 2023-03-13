package com.keduit.helloworld.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.helloworld.dto.BoardDTO;
import com.keduit.helloworld.dto.CommentDTO;
import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.Comment;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.service.BoardService;
import com.keduit.helloworld.service.CommentService;
import com.keduit.helloworld.service.FavoritesService;
import com.keduit.helloworld.service.MemberService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/hello/*")
public class MemberController {

	//호성
	
	private final MemberService memberService;

	private final BoardService boardService;
	
	private final CommentService commentService;
	
	private final FavoritesService favoritesService;
	
	@GetMapping("/spacepage")
	public void spacepage(Authentication authentication,String id, MemberDTO dto, Model model, Long memberNum) {
		Member idnum = memberService.idRead(id);
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Member me =  memberService.idRead(userDetails.getUsername());
		 
		 //내가 팔로한 사람인지
		 int mefollowing = favoritesService.getCount(me.getMemberNum(), idnum.getMemberNum());
		 
		
		//들어간사람이 팔로한사람
	    List<Member> folowing = memberService.getMemberMarker(idnum.getMemberNum());
	    //들어간사람을 팔로한 사람
	    List<Member> folower = memberService.getMemberMarked(idnum.getMemberNum());
	    
	    /** 들어간사람 쓴 글 보기 */
	   List<Board> myBoards = boardService.getMyBoardList(idnum.getId());
	   
	   /** 들어간사람 쓴 댓글 불러오기 */
	   List<CommentDTO> myComments = commentService.getCommentList(idnum.getMemberNum());
	   
	    model.addAttribute("member",idnum);
	    /**들어간사람 팔로한 사람 목록*/
	    model.addAttribute("following", folowing);
	    /** 들어간사람 팔로한 사람 목록 */
	    model.addAttribute("follower", folower);
	    /** 들어간사람 쓴 글 보기 */
	    model.addAttribute("myBoard",myBoards);
	    /** 들어간사람 쓴 댓글 보기 */
	    model.addAttribute("myComment", myComments);
	    /** 팔로한 사람인지 아닌지 확인하기*/
	    model.addAttribute("how", mefollowing);    
	}

	//별 클릭시 즐겨찾기 추가되면서 까만별로 바꾸기
	@GetMapping("/spacepage/insertF")
	public String insertF(Authentication authentication, String id, RedirectAttributes redirectAttributes) {
		//상대 정보 가져오기
		Member idnum = memberService.idRead(id);
		//내 아이디 가져오기
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Member me =  memberService.idRead(userDetails.getUsername());
		//값 insert하기
		favoritesService.register(me.getMemberNum(), idnum.getMemberNum());
		
		 /** 팔로한 사람인지 아닌지 확인하기*/
		 int mefollowing = favoritesService.getCount(me.getMemberNum(), idnum.getMemberNum());
		 
		 redirectAttributes.addAttribute("how", mefollowing);
		    
		return "redirect:/hello/spacepage?id="+id;
	}
	
	//까만 별 클릭시 즐겨찾기 삭제 후 하얀별로 변경
	@GetMapping("/spacepage/removeF")
	public String removeF(Authentication authentication, String id, RedirectAttributes redirectAttributes) {
		//내 아이디 가져오기
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Member me =  memberService.idRead(userDetails.getUsername());
		//상대 정보 가져오기
		Member idnum = memberService.idRead(id);
		
		//값 지우기
		favoritesService.remove(me.getMemberNum(),idnum.getMemberNum());
		
		
		 /** 팔로 삭제한 값 넘기기*/
		 redirectAttributes.addAttribute("how", "0");
		
		return "redirect:/hello/spacepage?id="+id;
	}
	
	
	//end 호성
}
