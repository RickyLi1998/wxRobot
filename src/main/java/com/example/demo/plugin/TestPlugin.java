package com.example.demo.plugin;

import com.example.demo.domain.Bot;
import com.example.demo.domain.BotPlugin;
import com.example.demo.domain.GroupMessageEvent;
import com.example.demo.domain.PrivateMessageEvent;
import org.springframework.stereotype.Component;

import static com.example.demo.config.MessageConfig.MESSAGE_BLANK;
import static com.example.demo.config.MessageConfig.MESSAGE_IN;

@Component
public class TestPlugin extends BotPlugin {
    @Override
    public int onPrivateMessage(Bot bot, PrivateMessageEvent event) {
        if (event.getMessage().equals("hi")){
            bot.sendPrivateMessage(event.getUserId(),"你好");
            return MESSAGE_BLANK;
        }
        if (event.getMessage().startsWith("我想听")){
            String musicName = event.getMessage().replace("我想听","").trim();
            bot.sendMusicMessage(event.getUserId(),musicName);
        }
        return MESSAGE_IN;
    }

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        if (event.getMessage().equals("hi")){
            bot.sendGroupMessage(event.getGroupId(),"hello~",event.getUserId(),event.getUsername(),false);
        }
        return MESSAGE_IN;
    }
}
