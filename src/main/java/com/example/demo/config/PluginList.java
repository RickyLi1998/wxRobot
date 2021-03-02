package com.example.demo.config;

import com.example.demo.domain.ApiEvent;
import com.example.demo.domain.Bot;
import com.example.demo.domain.GroupMessageEvent;
import com.example.demo.domain.PrivateMessageEvent;
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
}
