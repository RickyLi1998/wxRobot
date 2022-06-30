package com.example.demo.config;

import cn.hutool.json.JSONUtil;
import com.example.demo.domain.ApiEvent;
import com.example.demo.domain.Bot;
import com.example.demo.domain.BotPlugin;
import com.example.demo.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PluginList {
    private final List<BotPlugin> list;

    public void handle(ApiEvent event) {
        if (event != null) {
            switch (event.getType()) {
                case 100: //私聊事件
                    onPrivateMessage(event);
                    break;
                case 200: // 群聊事件
                    onGroupMessageEvent(event);
                    break;
                case 500: //收到好友请求事件
                    onFriendAddEvent(event);
                    break;
                case 400:
                    onGroupIncreaseNotice(event);
                    break;
                case 410:
                    onGroupDecreaseNotice(event);
                    break;
            }
        }
    }

    /**
     * 私聊事件
     */
    public PrivateMessageEvent onPrivateMessage(ApiEvent apiEvent) {
        PrivateMessageEvent event = new PrivateMessageEvent();
        event.setMessage(apiEvent.getMsg());
        event.setUserId(apiEvent.getFrom_wxid());
        event.setUsername(apiEvent.getFrom_name());
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (BotPlugin botPlugin : list) {
            int invoke = botPlugin.onPrivateMessage(bot, event);
            if (invoke != 0) {
                break;
            }
        }
        return event;
    }

    /**
     * 群聊事件
     */
    public GroupMessageEvent onGroupMessageEvent(ApiEvent apiEvent) {
        GroupMessageEvent event = new GroupMessageEvent();
        event.setMessage(apiEvent.getMsg());
        event.setUserId(apiEvent.getFinal_from_wxid());
        event.setUsername(apiEvent.getFinal_from_name());
        event.setGroupId(apiEvent.getFrom_wxid());
        event.setGroupName(apiEvent.getFrom_name());
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (BotPlugin botPlugin : list) {
            int invoke = botPlugin.onGroupMessage(bot, event);
            if (invoke != 0) {
                break;
            }
        }
        return event;
    }

    /**
     * 收到好友请求事件
     * */
    public FriendAddEvent onFriendAddEvent(ApiEvent apiEvent)  {
        FriendAddEvent event = new FriendAddEvent();
        event.setUserId(apiEvent.getFrom_wxid());
        event.setUsername(apiEvent.getFrom_name());
        event.setEventMsg(apiEvent.getMsg());
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (BotPlugin botPlugin : list) {
            int invoke = botPlugin.onFriendAddEvent( bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }


    /**
     * 群成增加
     * */
    public GroupIncreaseNoticeEvent onGroupIncreaseNotice(ApiEvent apiEvent)  {
        GroupIncreaseNoticeEvent event = new GroupIncreaseNoticeEvent();
        event.setGroupId(apiEvent.getFrom_wxid());
        event.setGroupName(apiEvent.getFrom_name());
        event.setInfo(JSONUtil.parseObj(apiEvent.getMsg()));
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (BotPlugin botPlugin : list) {
            int invoke = botPlugin.onGroupIncreaseNotice( bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }

    /**
     * 群成员减少
     * */
    public GroupDecreaseNoticeEvent onGroupDecreaseNotice(ApiEvent apiEvent)  {
        GroupDecreaseNoticeEvent event = new GroupDecreaseNoticeEvent();
        event.setGroupId(apiEvent.getFrom_wxid());
        event.setGroupName(apiEvent.getFrom_name());
        event.setInfo(JSONUtil.parseObj(apiEvent.getMsg()));
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (BotPlugin botPlugin : list) {
            int invoke = botPlugin.onGroupDecreaseNotice( bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }
}
