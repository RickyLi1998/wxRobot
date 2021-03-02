package com.example.demo.domain;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.util.EncodeUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.MessageConfig.*;

public class Bot {
    private String robotId;
    private String domain = "http://127.0.0.1:8073/send";

    public Bot(String robotId){
        this.robotId = robotId;
    }

    /**
     * 发送私聊消息
     * */
    public void sendPrivateMessage(String userId, String message){
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",userId);
        params.put("robot_wxid",robotId);
        params.put("type",PRIVATE_MESSAGE_TYPE);
        params.put("msg",message);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }

    /**
     * 发送群聊消息
     * */
    public void sendGroupMessage(String groupId, String message, String userId, String username, boolean isAt){
        Map<String,Object> params = new HashMap<>();
        if (isAt){
            params.put("to_wxid",groupId);
            params.put("robot_wxid",robotId);
            params.put("type",GROUP_MESSAGE_TYPE);
            params.put("msg",message);
            params.put("at_wxid",userId);
            params.put("at_name",username);
            JSONObject obj = JSONUtil.parseObj(params);
            HttpUtil.post(domain,obj.toString());
        }else {
            sendPrivateMessage(groupId,message);
        }
    }

    /**
     * 发送图片消息
     * */
    public void sendImageMessage(String toWxId, String imgPath){
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",toWxId);
        params.put("robot_wxid",robotId);
        params.put("type",IMAGE_MESSAGE_TYPE);
        params.put("msg",imgPath);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }

    /**
     * 发送视频消息
     * */
    public void sendVideoMessage(String toWxId, String videoPath){
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",toWxId);
        params.put("robot_wxid",robotId);
        params.put("type",VIDEO_MESSAGE_TYPE);
        params.put("msg",videoPath);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }

    /**
     * 发送文件消息
     * */
    public void sendFileMessage(String toWxId, String filePath){
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",toWxId);
        params.put("robot_wxid",robotId);
        params.put("type",FILE_MESSAGE_TYPE);
        params.put("msg",filePath);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }

    /**
     * 发送动态表情消息
     * */
    public void sendEmojiMessage(String toWxId, String emojiPath){
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",toWxId);
        params.put("robot_wxid",robotId);
        params.put("type",EMOJI_MESSAGE_TYPE);
        params.put("msg",emojiPath);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }

    /**
     * 分享链接
     * */
    public void sendLinkMessage(String toWxId, String title, String text, String url, String pic){
        Map<String, Object> image = new HashMap<>();
        image.put("title",EncodeUtil.encoded(title));
        image.put("text",text);
        image.put("url",url);
        image.put("pic",pic);
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",toWxId);
        params.put("robot_wxid",robotId);
        params.put("type",LINK_MESSAGE_TYPE);
        params.put("msg",image);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }

    /**
     * 分享音乐
     * */
    public void sendMusicMessage(String toWxId, String musicName){
        Map<String,Object> params = new HashMap<>();
        params.put("to_wxid",toWxId);
        params.put("robot_wxid",robotId);
        params.put("type",MUSIC_MESSAGE_TYPE);
        try {
            params.put("msg", URLEncoder.encode(musicName,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain,obj.toString());
    }
}
