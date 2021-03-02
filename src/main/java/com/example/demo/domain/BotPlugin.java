package com.example.demo.domain;

import com.sun.istack.internal.NotNull;

import static com.example.demo.config.MessageConfig.MESSAGE_IN;

public class BotPlugin {
    public int onPrivateMessage(@NotNull Bot bot, PrivateMessageEvent event){
        return MESSAGE_IN;
    }

    public int onGroupMessage(@NotNull Bot bot, GroupMessageEvent event){
        return MESSAGE_IN;
    }
}
