package com.keduit.helloworld.dto;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ChatListDTO {

    private static ChatListDTO instance = new ChatListDTO();
    private ConcurrentHashMap<Integer, ConcurrentLinkedQueue<ChatDTO>> chatListMap = new ConcurrentHashMap<>();

    private ChatListDTO() {}
    
    public static ChatListDTO getInstance() {
        return instance;
    }

    public void chatMapLog(int roomNumber, ChatDTO chatDTO) {
        ConcurrentLinkedQueue<ChatDTO> queue = chatListMap.getOrDefault(roomNumber, new ConcurrentLinkedQueue<>());
        if (queue.size() == 200) {
            queue.poll();
        }
        queue.add(chatDTO);
        chatListMap.put(roomNumber, queue);
    }

    public ConcurrentLinkedQueue<ChatDTO> getLog(int roomNumber) {
        ConcurrentLinkedQueue<ChatDTO> queue = chatListMap.get(roomNumber);
        if (queue == null) {
            return new ConcurrentLinkedQueue<>();
        }
        return queue;
    }



}
