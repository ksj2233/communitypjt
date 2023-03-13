package com.keduit.helloworld.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCurrentLocationDTO {
    private static UserCurrentLocationDTO getInstance = null;
    private ConcurrentHashMap<Integer, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private UserCurrentLocationDTO(){}

    public static UserCurrentLocationDTO getInstance(){
        if (getInstance == null) {
            getInstance = new UserCurrentLocationDTO();
        }
        return getInstance;
    }

    /** in user webSocketSession 유저의 PK와 그 세션을 저장한다.*/
    public void inUserWSS(Integer num, WebSocketSession session){
        sessionMap.put(num, session);
    }
    /** user webSocketSession log 유저의 PK를 주면 세션을 반환하고 없다면 null반환*/
    public WebSocketSession UserWSSLog(Integer num){
        if(sessionMap.containsKey(num)){
            return sessionMap.get(num);
        }else{
            return null;
        }
    }
    /** 세션값으로 데이터를 지움 */
    public void removeByValue(WebSocketSession session) {
        sessionMap.entrySet().removeIf(entry -> session.equals(entry.getValue()));
    }

}
