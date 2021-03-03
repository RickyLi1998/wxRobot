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
        if (event.getMessage().equals("名字")){
           bot.sendPrivateMessage(event.getUserId(), bot.getBotName());
        }
        if (event.getMessage().equals("头像")){
            bot.sendImageMessage(event.getUserId(), bot.getBotHeadPic());
        }
        if (event.getMessage().equals("机器人列表")){
            bot.getBotList();
        }
        if (event.getMessage().equals("好友列表")){
            bot.getFriendList(null,true);
        }
        if (event.getMessage().equals("群聊列表")){
            bot.getGroupList(null,true);
        }
        return MESSAGE_IN;
    }

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        if (event.getMessage().equals("hi")){
            bot.sendGroupMessage(event.getGroupId(),"hello~",event.getUserId(),event.getUsername(),false);
        }

        if (event.getMessage().equals("获取成员列表")){
            bot.getGroupMemberList(event.getGroupId(),true);
        }
        if (event.getMessage().equals("我得微信信息")){
            bot.sendGroupMessage(event.getGroupId(),
                    bot.getGroupMember(event.getGroupId(), event.getUserId()).toString(),
                    event.getUserId(),event.getUsername(),false);
        }
        if (event.getMessage().startsWith("修改群名")){
            bot.modifyGroupName(event.getGroupId(),
                    event.getMessage().replace("修改群名","")
            .trim());
        }
        return MESSAGE_IN;
    }
}
