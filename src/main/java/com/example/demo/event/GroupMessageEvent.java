package com.example.demo.event;

import lombok.Data;

@Data
public class GroupMessageEvent {
    private String userId;
    private String username;
    private String groupId;
    private String groupName;
    private String message;
    private int type;
}
