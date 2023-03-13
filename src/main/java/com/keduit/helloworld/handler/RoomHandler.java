package com.keduit.helloworld.handler;

import com.keduit.helloworld.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@Component
@Log4j2
@RequiredArgsConstructor
public class RoomHandler extends TextWebSocketHandler {

    // 전체 채팅방 유저 세션리스트 ArrayList 와 LinkedList 중에서 고를 필요 있음
    private List<WebSocketSession> userSessions = new LinkedList<>();
    private final ParserService parserService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = message.getPayload();
        JSONObject obj = parserService.jsonToObjectParser(msg);
        log.info("RoomHandler handleTextMessage obj : "+obj);

        // 세션 리스트에 있는 모든 세션에 메시지 전달
        for (WebSocketSession s : userSessions) {
            s.sendMessage(new TextMessage(obj.toString()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        super.afterConnectionEstablished(session);
//        JSONObject obj = parserService.jsonToObjectParser("test");
        userSessions.add(session);
//        session.sendMessage(new TextMessage(obj.toString()));
        log.info("RoomHandler afterConnectionEstablished session : "+session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        if(userSessions.size() > 0) { //소켓이 종료되면 해당 세션값들을 찾아서 지운다.
            for(int i = 0; i < userSessions.size(); i++) {
                userSessions.remove(session.getId());
            }
        }
        super.afterConnectionClosed(session, status);
    }

}
