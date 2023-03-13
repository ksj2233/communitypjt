//package com.keduit.helloworld.controller;
//
//import com.keduit.helloworld.dto.*;
//import com.keduit.helloworld.service.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@Controller
//@Log4j2
//@RequiredArgsConstructor
//@RequestMapping("/hello/*")
//public class AdminController {
//
//    private final AdminService adminService;
//    private final MemberService memberService;
//    private final BoardService boardService;
//    private final CommentService commentService;
//    private final MessageService messageService;
//    private final CouponService couponService;
//
//    @GetMapping("/admin")
//    public void adminPage(Model model,
//                          @RequestParam(defaultValue = "0") int memberPage,
//                          @RequestParam(defaultValue = "0") int boardPage,
//                          @RequestParam(defaultValue = "0") int commentPage,
//                          @RequestParam(defaultValue = "0") int messagePage,
//                          @RequestParam(defaultValue = "0") int couponPage) {
//        // 멤버 페이징 처리
//        PageRequest memberPageRequest = PageRequest.of(memberPage, 10, Sort.by(Sort.Direction.DESC, "memberNum"));
//
//        Page<MemberDTO> memberList;
//
//        memberList = memberService.getMembers(memberPageRequest);
//
//
//        model.addAttribute("memberList", memberList);
//
//        // 게시물 페이징 처리
//        PageRequest boardPageRequest = PageRequest.of(boardPage, 10, Sort.by(Sort.Direction.DESC, "boardNum"));
//
//        Page<BoardDTO> boardList;
//
//        boardList = boardService.getBoards(boardPageRequest);
//
//        model.addAttribute("boardList", boardList);
//
//        // 댓글 페이징 처리
//
//        PageRequest commentPageRequest = PageRequest.of(commentPage, 10, Sort.by(Sort.Direction.DESC, "boardCommentNum"));
//        Page<CommentDTO> commentList;
//
//        commentList = commentService.getComments(commentPageRequest);
//
//        model.addAttribute("commentList", commentList);
//
//        // 메시지 페이징 처리
//        PageRequest messagePageRequest = PageRequest.of(messagePage, 10, Sort.by(Sort.Direction.DESC, "messageNum"));
//        Page<MessageDTO> messageList;
//
//        messageList = messageService.getMessages(messagePageRequest);
//
//        model.addAttribute("messageList", messageList);
//
//        PageRequest commentPageReq = PageRequest.of(couponPage, 10,Sort.by(Sort.Direction.DESC,"couponNum"));
//        Page<CouponDTO> couponList = couponService.readCouponList(commentPageReq);
//        model.addAttribute("couponList",couponList);
//    }
//
//    @GetMapping("/createCoupon")
//    public String createCoupon(){
//        couponService.couponCreate();
//        return "redirect:/hello/admin";
//    }
//
//    @PostMapping("/admin/member")
//    public List<MemberDTO> search (@RequestParam HashMap<Object,Object> params, Model model){
////        PageRequest memberPageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "memberNum"));
////        PageRequest boardPageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "boardNum"));
////        PageRequest commentPageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "boardCommentNum"));
////        PageRequest messagePageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "messageNum"));
////        PageRequest commentPageReq = PageRequest.of(0, 10,Sort.by(Sort.Direction.DESC,"couponNum"));
////
////        Page<MemberDTO> memberList = memberService.getMembers(memberPageRequest);
////        Page<BoardDTO> boardList = boardService.getBoards(boardPageRequest);
////        Page<CommentDTO> commentList = commentService.getComments(commentPageRequest);
////        Page<MessageDTO> messageList = messageService.getMessages(messagePageRequest);
////        Page<CouponDTO> couponList = couponService.readCouponList(commentPageReq);
////
////        model.addAttribute("memberList", memberList);
////        model.addAttribute("boardList", boardList);
////        model.addAttribute("commentList", commentList);
////        model.addAttribute("messageList", messageList);
////        model.addAttribute("couponList",couponList);
//
//
//        String memberSel = params.get("memberSel").toString();
//        String memberVal = params.get("memberVel").toString();
//
//        List<MemberDTO> list = new ArrayList<>();
//        return list;
//    }
//
//}
