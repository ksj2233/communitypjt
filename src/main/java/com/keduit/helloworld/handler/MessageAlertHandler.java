package com.keduit.helloworld.handler;

import com.keduit.helloworld.dto.UserCurrentLocationDTO;
import com.keduit.helloworld.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@Component
@Log4j2
@RequiredArgsConstructor
public class MessageAlertHandler extends TextWebSocketHandler {

    private final ParserService parserService;

    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        //여기와서 발송됨

    }

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        super.afterConnectionEstablished(session);
        String sessionId = session.getId();
        JSONObject obj = parserService.jsonToObjectParser(sessionId);
        session.sendMessage(new TextMessage(obj.toString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        UserCurrentLocationDTO.getInstance().removeByValue(session);
        super.afterConnectionClosed(session, status);
    }
}
