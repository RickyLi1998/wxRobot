package com.example.demo.plugin;

import com.example.demo.domain.Bot;
import com.example.demo.domain.BotPlugin;
import com.example.demo.event.GroupMessageEvent;
import com.example.demo.event.PrivateMessageEvent;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.example.demo.config.MessageConfig.MESSAGE_IN;

@Slf4j
@Component
@Order(Integer.MIN_VALUE)
public class LogPlugin implements BotPlugin {
    /**
     * 私聊消息
     * bot 机器人对象
     * event 私聊事件对象
     * */
    @Override
    public int onPrivateMessage(@NotNull Bot bot, PrivateMessageEvent event) {
        String message = event.getMessage(); // 接收的消息
        String userId = event.getUserId(); // 发送人id
        String username = event.getUsername(); // 发送人名称
        int msgType = event.getType(); // 1文本消息 3图片消息 34语音消息

        log.info("收到私聊消息 昵称: {} 消息内容 {}",username,message);
        return MESSAGE_IN;
    }

    /**
     * 群聊消息
     * bot 机器人对象
     * event 群聊事件对象
     * */
    @Override
    public int onGroupMessage(@NotNull Bot bot, GroupMessageEvent event) {
        String message = event.getMessage(); // 接收的消息
        String userId = event.getUserId(); // 发送人id
        String username = event.getUsername(); // 发送人名称
        String groupId = event.getGroupId(); // 群id
        String groupName = event.getGroupName(); // 群名称
        int msgType = event.getType(); // 1文本消息 3图片消息 34语音消息

        log.info("收到群聊消息 群名: {} 昵称 {} 消息内容 {}",groupName,username,message);
        return MESSAGE_IN;
    }
}
