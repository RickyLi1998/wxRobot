package com.example.demo.event;

import lombok.Data;

@Data
public class FriendAddEvent {
    private String userId;
    private String username;
    private String eventMsg;
}
