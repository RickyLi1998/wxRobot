package com.example.demo.domain;

import lombok.Data;

@Data
public class PrivateMessageEvent {
    private String userId;
    private String username;
    private String message;

}
