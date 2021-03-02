package com.example.demo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ApiEvent {
    private int type;
    private int msg_type;
    private String from_wxid;
    private String final_from_wxid;
    private String from_name;
    private String final_from_name;
    private String robot_wxid;
    private String msg;
    private long time;
    private int rid;
}
