package com.keduit.helloworld.controller;

import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.RoomDTO;
import com.keduit.helloworld.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    // 서버에 있는 전체 방 갯수
    private int roomNumber = 0;

    // 서버에 있는 roomNumber and name List
    private List<RoomDTO> roomList = new ArrayList<>();

    private final MemberService memberService;



    @RequestMapping("/chat")
    public ModelAndView chat() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chat");
        log.info("WebSocketController chat mv : "+mv);
        return mv;
    }

    /**
     * 방 페이지
     * @return
     */
    @RequestMapping("/room")
    public ModelAndView room(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        /** 쪽지 보낸사람 회원번호로, 받는사람 회원정보 가져오기(read) */
        MemberDTO memberDTO = memberService.getMyInfo(userDetails.getUsername());

        ModelAndView mv = new ModelAndView();
        mv.setViewName("room");
        mv.addObject("member",memberDTO);
        log.info("mv : "+mv);
        return mv;
    }

    /**
     * 방 생성하기
     * @param params
     * @return
     */
    @RequestMapping("/createRoom")
    public @ResponseBody List<RoomDTO> createRoom(@RequestParam HashMap<Object, Object> params,
                                                  Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        /** 쪽지 보낸사람 회원번호로, 받는사람 회원정보 가져오기(read) */
        MemberDTO memberDTO = memberService.getMyInfo(userDetails.getUsername());

        String roomName = (String) params.get("roomName");
        log.info("ChatController createRoom");
        if(roomName != null &&
                !roomName.trim().equals("") &&
                containValue(roomList, memberDTO.getMemberNum(),roomName)) {
            RoomDTO roomdto = new RoomDTO();
            roomdto.setRoomNumber(++roomNumber);
            roomdto.setRoomName(roomName);
            roomdto.setMemberNum(memberDTO.getMemberNum());
            roomList.add(roomdto);
        }
        log.info("WebSocketController createRoom roomList : "+roomList);
        return roomList;
    }

    @RequestMapping("/deleteRoom")
    public @ResponseBody List<RoomDTO> deleteRoom(@RequestParam HashMap<Object, Object> params){
        log.info("WebSocketController deleteRoom params : "+params);
        String roomNumber = (String) params.get("roomNumber");
        String roomName = (String) params.get("roomName");
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomName(roomName);
        roomDTO.setRoomNumber(Integer.parseInt(roomNumber));
        log.info("WebSocketController deleteRoom roomDTO : "+roomDTO);
        roomList.remove(roomDTO);
        return roomList;
    }

    /**
     * 방 정보가져오기
     * @param params
     * @return
     */
    @RequestMapping("/getRoom")
    public @ResponseBody List<RoomDTO> getRoom(@RequestParam HashMap<Object, Object> params){
        log.info("WebSocketController getRoom roomList : "+roomList);
        return roomList;
    }

    /**
     * 채팅방
     * @return
     */
    @RequestMapping("/moveChatting")
    public ModelAndView chatting(@RequestParam HashMap<Object, Object> params,
                                 Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        /** 쪽지 보낸사람 회원번호로, 받는사람 회원정보 가져오기(read) */
        MemberDTO memberDTO = memberService.getMyInfo(userDetails.getUsername());

        ModelAndView mv = new ModelAndView();
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));

        List<RoomDTO> new_list = roomList
                .stream()
                .filter(o->o.getRoomNumber()==roomNumber)
                .collect(Collectors.toList());
        if(new_list != null && new_list.size() > 0) {
            mv.addObject("roomName", params.get("roomName"));
            mv.addObject("roomNumber", params.get("roomNumber"));
            mv.addObject("member",memberDTO);
            mv.setViewName("chat");
        }else {
            mv.addObject("member",memberDTO);
            mv.setViewName("room");
        }

        log.info("WebSocketController chatting mv : "+mv);
        return mv;
    }

    private boolean containValue(List<RoomDTO> list, Long memberNum,String roomName){
        for(RoomDTO roomDTO : list){
            if(roomDTO.getMemberNum() == memberNum) return false;
            if(roomDTO.getRoomName().equals(roomName)) return false;
        }
        return true;
    }
}
