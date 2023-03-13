package com.keduit.helloworld.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.helloworld.dto.BoardDTO;
import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.PageRequestDTO;
import com.keduit.helloworld.dto.PageResultDTO;
import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.BoardRepository;
import com.keduit.helloworld.service.BoardService;
import com.keduit.helloworld.service.CommentService;
import com.keduit.helloworld.service.MemberService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/hello/*")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	private final BoardService boardService;
	
	private final MemberService memberService;
	
	private final CommentService commentService;
	
	
	@GetMapping("/modal")
	public void modal() {
		
	}
	
	
	// < list >
	// GetMapping
    @GetMapping("/communitylist")
    public void communitylist(Authentication authentication, PageRequestDTO pageRequestDTO,Model model){
    	log.info("위치 : BoardController communitylist()");
    	log.info("pageRequestDTO : " + pageRequestDTO);
    	PageResultDTO<BoardDTO, Object[]> result = boardService.getBoard1List(pageRequestDTO);
    	log.info("뿌림?" + result);
    	

    	if(authentication != null) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Member idnum =  memberService.idRead(userDetails.getUsername());
		model.addAttribute("member",idnum);
		
		System.out.println("username = " + userDetails.getUsername());
	    System.out.println("role = " + userDetails.getAuthorities().stream().map(r -> String.valueOf(r)).collect(Collectors.joining(",")));
    	}
	    
	    //호성 top Community 제작 23.02.22
	    
	    /** 보드  limit 5 */
	    List<Board> topBoard = boardService.topboard();
	    /** 맴버  limit 5 */
	    List<Member> topMembers = memberService.topMember();
	    
//	    System.out.println("탑보드 확인하자"+topBoard); 
	    System.out.println("맴버값 확인하자"+topBoard); 
	    model.addAttribute("top",topBoard);
	    model.addAttribute("topuser",topMembers);
	    
	    // 호성 end
	    
	    
	    
    	
		model.addAttribute("result",boardService.getBoard1List(pageRequestDTO));
	}
    @GetMapping("/noticelist")
    public void noticelist(PageRequestDTO pageRequestDTO,Model model){
    	log.info("위치 : BoardController noticelist()");
    	log.info("pageRequestDTO : " + pageRequestDTO);
    	PageResultDTO<BoardDTO, Object[]> result = boardService.getBoard2List(pageRequestDTO);
    	log.info("2뿌림?" + result);
    	
		model.addAttribute("result",boardService.getBoard2List(pageRequestDTO));
    }
    @GetMapping("/qnalist")
    public void qnalist(PageRequestDTO pageRequestDTO,Model model){
    	log.info("위치 : BoardController qnalist()");
    	log.info("pageRequestDTO : " + pageRequestDTO);
    	PageResultDTO<BoardDTO, Object[]> result = boardService.getBoard3List(pageRequestDTO);
    	log.info("뿌림?" + result);
    	
		model.addAttribute("result",boardService.getBoard3List(pageRequestDTO));
    }
    
	
    
	// < resister >
	// PostMapping
	@PostMapping("/communityregister")
	public String communityregister(Authentication authentication,BoardDTO dto, RedirectAttributes redirectAttributes) {
		log.info("위치 : BoardController register()");
		log.info("dto : " + dto);
		Long boardnum = boardService.register(dto);
		
		//23.02.28 호성
		// 게시물 등록시 경험치 추가 
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		MemberDTO exe = memberService.exeModify(userDetails.getUsername());
		
		//호성 end
		log.info("boardnum : " + boardnum);
		redirectAttributes.addFlashAttribute("msg",boardnum);
		return "redirect:/hello/communitylist";
	}
	
	@PostMapping("/noticeregister")
	public String noticeregister(BoardDTO dto, RedirectAttributes redirectAttributes) {
		log.info("위치 : BoardController register()");
		log.info("dto : " + dto);
		Long boardnum = boardService.register(dto);
		
		log.info("boardnum : " + boardnum);
		redirectAttributes.addFlashAttribute("msg",boardnum);
		return "redirect:/hello/noticelist";
	}	
	
	@PostMapping("/qnaregister")
	public String qnaregister(Authentication authentication, BoardDTO dto, RedirectAttributes redirectAttributes) {
		log.info("위치 : BoardController register()");
		log.info("dto : " + dto);
		Long boardnum = boardService.register(dto);
		//23.02.28 호성
				// 게시물 등록시 경험치 추가 
				UserDetails userDetails = (UserDetails)authentication.getPrincipal();
				MemberDTO exe = memberService.exeModify(userDetails.getUsername());
				
				//호성 end
		
		
		log.info("boardnum : " + boardnum);
		redirectAttributes.addFlashAttribute("msg",boardnum);
		return "redirect:/hello/qnalist";
	}
	
	
	// GetMapping
	@GetMapping({"/communityregister","/noticeregister","/qnaregister"})
	public void register(Authentication authentication,Model model) {
		log.info("등록페이지");
		
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		
		System.out.println("username = " + userDetails.getUsername());
	    System.out.println("role = " + userDetails.getAuthorities().stream().map(r -> String.valueOf(r)).collect(Collectors.joining(",")));
		
	    Member idnum =  memberService.idRead(userDetails.getUsername());
	    
	    model.addAttribute("member",idnum);
		
	}
	
	
	
	
	// < read >
	// GetMapping
	@GetMapping({"/communityread", "/noticeread","/qnaread"})
	public void read(Authentication authentication,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , Long boardNum , Model model) {
		
		log.info("위치 : BoardController read()");
		log.info("boardNum : " + boardNum);
		
		Board board = boardRepository.findById(boardNum).get();
		Long countViews = board.getViews()+1L;
		
		BoardDTO boardDTO = BoardDTO.builder().views(countViews).build();
		
		boardService.updateViews(boardNum, boardDTO);
		
		boardDTO = boardService.get(boardNum);
		
		log.info(boardDTO);

		
//		추가
		
		if(authentication != null) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		
		System.out.println("username = " + userDetails.getUsername());
	    System.out.println("role = " + userDetails.getAuthorities().stream().map(r -> String.valueOf(r)).collect(Collectors.joining(",")));
		
	    Member idnum =  memberService.idRead(userDetails.getUsername());
	    
	    model.addAttribute("member",idnum);
		}
//	    
	    
		model.addAttribute("dto",boardDTO);
		
	}
	
	
	
	
	// < modify >
	// PostMapping
	@PostMapping("/communitymodify")
	public String communitymodify(BoardDTO boardDTO,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , RedirectAttributes redirectAttributes) {
		log.info("위치 : BoardController modify()");
		log.info("dto : " + boardDTO);
		
		boardService.modify(boardDTO);
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addAttribute("type", pageRequestDTO.getType());
		redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
		redirectAttributes.addAttribute("boardNum", boardDTO.getBoardNum());
		
		return "redirect:/hello/communityread";
	}

	@PostMapping("/noticemodify")
	public String noticemodify(BoardDTO boardDTO,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , RedirectAttributes redirectAttributes) {
		log.info("위치 : BoardController modify()");
		log.info("dto : " + boardDTO);
		
		boardService.modify(boardDTO);
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addAttribute("type", pageRequestDTO.getType());
		redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
		redirectAttributes.addAttribute("boardNum", boardDTO.getBoardNum());
		
		return "redirect:/hello/noticeread";
	}
	
	@PostMapping("/qnamodify")
	public String qnamodify(BoardDTO boardDTO,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , RedirectAttributes redirectAttributes) {
		log.info("위치 : BoardController modify()");
		log.info("dto : " + boardDTO);
		
		boardService.modify(boardDTO);
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addAttribute("type", pageRequestDTO.getType());
		redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
		redirectAttributes.addAttribute("boardNum", boardDTO.getBoardNum());
		
		return "redirect:/hello/qnaread";
	}

	
	// GetMapping
	@GetMapping("/communitymodify")
	public void communitymodify(Authentication authentication,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , Long boardNum , Model model) {
		
		log.info("위치 : BoardController modify()");
		log.info("bno : " + boardNum);
		BoardDTO boardDTO = boardService.get(boardNum);
		
		log.info(boardDTO);
		
//		추가
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		
		System.out.println("username = " + userDetails.getUsername());
	    System.out.println("role = " + userDetails.getAuthorities().stream().map(r -> String.valueOf(r)).collect(Collectors.joining(",")));
		
	    Member idnum =  memberService.idRead(userDetails.getUsername());
	    
	    model.addAttribute("member",idnum);
//	    
		model.addAttribute("dto",boardDTO);
		
	}
	
	@GetMapping("/noticemodify")
	public void noticemodify(Authentication authentication,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , Long boardNum , Model model) {
		
		log.info("위치 : BoardController modify()");
		log.info("bno : " + boardNum);
		BoardDTO boardDTO = boardService.get(boardNum);
		
		log.info(boardDTO);
		
//		추가
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		
		System.out.println("username = " + userDetails.getUsername());
	    System.out.println("role = " + userDetails.getAuthorities().stream().map(r -> String.valueOf(r)).collect(Collectors.joining(",")));
		
	    Member idnum =  memberService.idRead(userDetails.getUsername());
	    
	    model.addAttribute("member",idnum);
//	    
		model.addAttribute("dto",boardDTO);
		
	}
	
	@GetMapping("/qnamodify")
	public void qnamodify(Authentication authentication,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO , Long boardNum , Model model) {
		
		log.info("위치 : BoardController modify()");
		log.info("bno : " + boardNum);
		BoardDTO boardDTO = boardService.get(boardNum);
		
		log.info(boardDTO);
		
//		추가
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		
		System.out.println("username = " + userDetails.getUsername());
	    System.out.println("role = " + userDetails.getAuthorities().stream().map(r -> String.valueOf(r)).collect(Collectors.joining(",")));
		
	    Member idnum =  memberService.idRead(userDetails.getUsername());
	    
	    model.addAttribute("member",idnum);
//	    
		model.addAttribute("dto",boardDTO);
		
	}
	
	
	
	
	// < remove >
	// PostMapping
	@PostMapping("/communityremove")
	public String communityremove(long boardNum, RedirectAttributes redirectAttributes) {
		
		log.info("위치 : BoardController remove()");
		log.info("boardNum : " + boardNum);
		boardService.remove(boardNum);
		commentService.boardRemove(boardNum);
		redirectAttributes.addFlashAttribute("msg",boardNum);
		return "redirect:/hello/communitylist";
	}
	
	@PostMapping("/noticeremove")
	public String noticeremove(long boardNum, RedirectAttributes redirectAttributes) {
		
		log.info("위치 : BoardController remove()");
		log.info("boardNum : " + boardNum);
		boardService.remove(boardNum);
		commentService.boardRemove(boardNum);
		redirectAttributes.addFlashAttribute("msg",boardNum);
		return "redirect:/hello/noticelist";
	}
	
	@PostMapping("/qnaremove")
	public String qnaremove(long boardNum, RedirectAttributes redirectAttributes) {
		
		log.info("위치 : BoardController remove()");
		log.info("boardNum : " + boardNum);
		boardService.remove(boardNum);
		commentService.boardRemove(boardNum);
		redirectAttributes.addFlashAttribute("msg",boardNum);
		return "redirect:/hello/qnalist";
	}
}
