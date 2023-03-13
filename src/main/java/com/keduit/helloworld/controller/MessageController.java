package com.keduit.helloworld.controller;

import java.util.HashMap;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.helloworld.dto.CommentDTO;
import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.MessageDTO;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.service.MemberService;
import com.keduit.helloworld.service.MessageService;
import com.nimbusds.jose.shaded.json.JSONObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/hello/*")
public class MessageController {

	private final MessageService messageService;
	private final MemberService memberService;

	@PostMapping("/message/register")
	/** 쪽지 등록 = 전송 = 답장 */
	public String register(@RequestParam HashMap<Object, Object> params, Authentication authentication) {
		// 꺼진불도 다시보자 正

		String title = params.get("title").toString();
		String content = params.get("content").toString();
		String yourNum= params.get("yourNum").toString();
		String boardCommentNum = "";
		
		if(params.get("boardCommentNum") != null){
			boardCommentNum = params.get("boardCommentNum").toString();
		}

		log.info("MessageController register");
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // 멤버 pk = 회원번호
		
		/** 조회하는사람 회원번호로, 본인 정보 가져오기 */
		MemberDTO myInfoDTO = memberService.getMyInfo(userDetails.getUsername());

		MemberDTO yourMemDTO = memberService.read(Long.parseLong(yourNum)); //상대정보
		
		MessageDTO msgDTO;
		
		if(boardCommentNum.equals("")){ //게시판 통해 전송 시
			Long messageNum  = Long.parseLong(params.get("messageNum").toString()); //쪽지번호

			/** 쪽지 번호, 조회하는 사람 회원번호로 쪽지 상세 조회하기 */
			MessageDTO messageDTO = messageService.read(messageNum, myInfoDTO.getMemberNum());
			//쪽지번호는 두번째 부터 존재(최초전송시 쪽지번호 없음)

			msgDTO = MessageDTO
					.builder()
					.memberGet(yourMemDTO.getMemberNum())
					.memberGive(myInfoDTO.getMemberNum())
					.boardCommentNum(messageDTO.getBoardCommentNum())
					.title(title)
					.content(content)
					.view(0L)
					.build();

		}else{ //쪽지함에서 답장 시
			msgDTO = MessageDTO
					.builder()
					.memberGet(Long.parseLong(yourNum))
					.memberGive(myInfoDTO.getMemberNum())
					.boardCommentNum(Long.parseLong(boardCommentNum)) //쪽지 정보에 있는 댓글번호 가져옴
					.title(title)
					.content(content)
					.view(0L)
					.build();
		}

		
		Long returnNum = messageService.register(msgDTO);
		
		return "redirect:/hello/message";
		
	}
	
	@GetMapping("/message")
	/** 쪽지함 메인 = 쪽지 목록(list) */
	public void message(Authentication authentication, Model model) {  // 꺼진불도 다시보자 正
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // 멤버 pk = 회원번호
		
		/** 조회하는사람 회원번호로, 본인 정보 가져오기 */
		MemberDTO myInfoDTO = memberService.getMyInfo(userDetails.getUsername());
		
		/** 조회하는 사람 회원번호로, 쪽지 목록 조회하기 */ 
		List<MessageDTO> msgGive = messageService.getListAsGiver(myInfoDTO.getMemberNum()); //조회하는 사람이 발신자 
		List<MessageDTO> msgGet = messageService.getListAsGetter(myInfoDTO.getMemberNum()); //조회하는 사람이 수신자
		
		model.addAttribute("myInfo",myInfoDTO); //내정보
		model.addAttribute("giveMsg", msgGive); //보낸 쪽지 목록
		model.addAttribute("getMsg", msgGet); //받은 쪽지 목록
	}
	
	@PostMapping("/message/read")
	/** 쪽지 상세 조회(read) */
	public ResponseEntity<MessageDTO> read(Long messageNum, Authentication authentication, Model model) { // 꺼진불도 다시보자 正
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // 멤버 pk = 회원번호
		
		/** 조회하는사람 회원번호로, 본인 정보 가져오기 */
		MemberDTO myInfoDTO = memberService.getMyInfo(userDetails.getUsername()); //내정보
		
		/** 쪽지 번호, 조회하는 사람 회원번호로 쪽지 상세 조회하기 */
		MessageDTO messageDTO = messageService.read(messageNum, myInfoDTO.getMemberNum());

		model.addAttribute("messageDTO", messageDTO);
		
		return new ResponseEntity<>(messageDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/message/delete")
	/** 쪽지 삭제 */
	public ResponseEntity<String> deleteMessage(@RequestParam HashMap<Object, Object> params, Authentication authentication) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // 멤버 pk = 회원번호
		
		Long messageNum = Long.parseLong(params.get("messageNum").toString()); //쪽지번호
		Long view = Long.parseLong(params.get("view").toString()); //보기권한

		/** 조회하는사람 회원번호로, 본인 정보 가져오기 */
		MemberDTO myInfoDTO = memberService.getMyInfo(userDetails.getUsername());
		
		/** 쪽지 번호, 조회하는 사람 회원번호로 쪽지 상세 조회하기 */
		MessageDTO messageDTO = messageService.read(messageNum, myInfoDTO.getMemberNum());
		
		if(messageDTO.getMemberGet() == myInfoDTO.getMemberNum()) { //삭제 누른 사람이 수신자면 
			messageService.modifyViewAsGetter(messageNum, view); 	//받은사람 view 변경 & 3일때 삭제 수행
		}else { 													//아니면(삭제 누른사람이 발신자면),
			messageService.modifyViewAsGiver(messageNum, view); 	//보낸사람 view 변경 & 3일때 삭제 수행
		}
		
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
}
