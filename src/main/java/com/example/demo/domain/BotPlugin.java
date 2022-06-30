package com.example.demo.domain;

import com.example.demo.event.*;
import com.sun.istack.internal.NotNull;

import static com.example.demo.config.MessageConfig.MESSAGE_IN;

public interface BotPlugin {
    /**
     * 收到私聊消息
     */
    default int onPrivateMessage(@NotNull Bot bot, PrivateMessageEvent event) {
        return MESSAGE_IN;
    }

    /**
     * 收到群聊消息
     */
    default int onGroupMessage(@NotNull Bot bot, GroupMessageEvent event) {
        return MESSAGE_IN;
    }

    /**
     * 收到添加好友请
     */
    default int onFriendAddEvent(@NotNull Bot bot, FriendAddEvent event) {
        return MESSAGE_IN;
    }

    /**
     * 群成员增加
     */
    default int onGroupIncreaseNotice(@NotNull Bot bot, GroupIncreaseNoticeEvent event) {
        return MESSAGE_IN;
    }

    /**
     * 群成员减少
     */
    default int onGroupDecreaseNotice(@NotNull Bot bot, GroupDecreaseNoticeEvent event) {
        return MESSAGE_IN;
    }
}
