package com.example.demo.domain;

import com.example.demo.event.*;
import com.sun.istack.internal.NotNull;

import static com.example.demo.config.MessageConfig.MESSAGE_IN;

public class BotPlugin {
    /**
     * 收到私聊消息
     * */
    public int onPrivateMessage(@NotNull Bot bot, PrivateMessageEvent event){
        return MESSAGE_IN;
    }

    /**
     * 收到群聊消息
     * */
    public int onGroupMessage(@NotNull Bot bot, GroupMessageEvent event){
        return MESSAGE_IN;
    }

    /**
     * 收到添加好友请
     * */
    public int onFriendAddEvent(@NotNull Bot bot, FriendAddEvent event){
        return MESSAGE_IN;
    }

    /**
     * 群成员增加
     * */
    public int onGroupIncreaseNotice(@NotNull Bot bot, GroupIncreaseNoticeEvent event){
        return MESSAGE_IN;
    }

    /**
     * 群成员减少
     * */
    public int onGroupDecreaseNotice(@NotNull Bot bot, GroupDecreaseNoticeEvent event){
        return MESSAGE_IN;
    }
}
