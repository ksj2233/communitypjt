package com.keduit.helloworld.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class ChatDTO {


    private Long userNum;
    private String type;
    private String userName;
    private String message;
    private LocalDateTime regDate;

}
