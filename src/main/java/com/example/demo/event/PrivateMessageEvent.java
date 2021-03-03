package com.example.demo.event;

import lombok.Data;

@Data
public class PrivateMessageEvent {
    private String userId;
    private String username;
    private String message;
    private int type;
}
