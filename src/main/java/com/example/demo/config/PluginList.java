package com.example.demo.config;

import cn.hutool.json.JSONUtil;
import com.example.demo.domain.ApiEvent;
import com.example.demo.domain.Bot;
import com.example.demo.event.*;
import com.example.demo.util.SpringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "plugin")
public class PluginList {
    private List<Class<Object>> list;

    public void setList(List<Class<Object>> list) {
        this.list = list;
    }

    public List<Class<Object>> getList() {
        return list;
    }

    public void handle(ApiEvent event) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (event != null){
            switch (event.getType()){
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
     * */
    public PrivateMessageEvent onPrivateMessage(ApiEvent apiEvent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        PrivateMessageEvent event = new PrivateMessageEvent();
        event.setMessage(apiEvent.getMsg());
        event.setUserId(apiEvent.getFrom_wxid());
        event.setUsername(apiEvent.getFrom_name());
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (Class o : list) {
            Object bean = SpringUtil.getBean(o);
            Method method = o.getMethod("onPrivateMessage", Bot.class, PrivateMessageEvent.class);
            int invoke = (int) method.invoke(bean, bot,event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }

    /**
     * 群聊事件
     * */
    public GroupMessageEvent onGroupMessageEvent(ApiEvent apiEvent) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        GroupMessageEvent event = new GroupMessageEvent();
        event.setMessage(apiEvent.getMsg());
        event.setUserId(apiEvent.getFinal_from_wxid());
        event.setUsername(apiEvent.getFinal_from_name());
        event.setGroupId(apiEvent.getFrom_wxid());
        event.setGroupName(apiEvent.getFrom_name());
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (Class o : list) {
            Object bean = SpringUtil.getBean(o);
            Method method = o.getMethod("onGroupMessage", Bot.class, GroupMessageEvent.class);
            int invoke = (int) method.invoke(bean, bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }

    /**
     * 收到好友请求事件
     * */
    public FriendAddEvent onFriendAddEvent(ApiEvent apiEvent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FriendAddEvent event = new FriendAddEvent();
        event.setUserId(apiEvent.getFrom_wxid());
        event.setUsername(apiEvent.getFrom_name());
        event.setEventMsg(apiEvent.getMsg());
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (Class o : list) {
            Object bean = SpringUtil.getBean(o);
            Method method = o.getMethod("onFriendAddEvent", Bot.class, FriendAddEvent.class);
            int invoke = (int) method.invoke(bean, bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }


    /**
     * 群成增加
     * */
    public GroupIncreaseNoticeEvent onGroupIncreaseNotice(ApiEvent apiEvent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GroupIncreaseNoticeEvent event = new GroupIncreaseNoticeEvent();
        event.setGroupId(apiEvent.getFrom_wxid());
        event.setGroupName(apiEvent.getFrom_name());
        event.setInfo(JSONUtil.parseObj(apiEvent.getMsg()));
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (Class o : list) {
            Object bean = SpringUtil.getBean(o);
            Method method = o.getMethod("onGroupIncreaseNotice", Bot.class, GroupIncreaseNoticeEvent.class);
            int invoke = (int) method.invoke(bean, bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }

    /**
     * 群成员减少
     * */
    public GroupDecreaseNoticeEvent onGroupDecreaseNotice(ApiEvent apiEvent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GroupDecreaseNoticeEvent event = new GroupDecreaseNoticeEvent();
        event.setGroupId(apiEvent.getFrom_wxid());
        event.setGroupName(apiEvent.getFrom_name());
        event.setInfo(JSONUtil.parseObj(apiEvent.getMsg()));
        Bot bot = new Bot(apiEvent.getRobot_wxid());
        for (Class o : list) {
            Object bean = SpringUtil.getBean(o);
            Method method = o.getMethod("onGroupDecreaseNotice", Bot.class, GroupDecreaseNoticeEvent.class);
            int invoke = (int) method.invoke(bean, bot, event);
            if (invoke != 0){
                break;
            }
        }
        return event;
    }
}
