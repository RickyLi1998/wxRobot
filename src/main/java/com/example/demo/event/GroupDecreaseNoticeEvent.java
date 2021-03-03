package com.example.demo.event;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class GroupDecreaseNoticeEvent {
    private String groupId;
    private String groupName;
    private JSONObject info;
}
