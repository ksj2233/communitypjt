package com.keduit.helloworld.config;

import com.keduit.helloworld.handler.ChatHandler;
import com.keduit.helloworld.handler.MessageAlertHandler;
import com.keduit.helloworld.handler.RoomHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Log4j2
public class WebSocketConfig implements WebSocketConfigurer{

    @Autowired
    private ChatHandler chatHandler;
    @Autowired
    private RoomHandler roomHandler;
    @Autowired
    private MessageAlertHandler messageAlertHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("registry : "+registry);
        registry.addHandler(chatHandler, "/chatting/{roomNumber}");
        registry.addHandler(roomHandler, "/reloadRoom");
        registry.addHandler(messageAlertHandler, "/messageAlert");
    }
}