package com.example.demo.domain;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.event.FriendAddEvent;
import com.example.demo.util.EncodeUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.MessageConfig.*;

public class Bot {
    private String robotId;
    private String domain = "http://127.0.0.1:8073/send";
    private  Map<String,Object> params = null;

    public Bot(String robotId){
        this.robotId = robotId;
    }

    /**
     * 发送私聊消息
     * */
    public void sendPrivateMessage(String userId, String message){
        params = new HashMap<>();
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
        params = new HashMap<>();
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
        params = new HashMap<>();
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
        params = new HashMap<>();
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
        params = new HashMap<>();
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
        params = new HashMap<>();
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

        params = new HashMap<>();
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
        params = new HashMap<>();
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

    /**
     * 获取机器人账号昵称
     * */
    public String getBotName(){
        params = new HashMap<>();
        params.put("robot_wxid",robotId);
        params.put("type",GET_ROBOT_NAME);
        JSONObject obj = JSONUtil.parseObj(params);
        String name = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString())).getStr("data");
        return name;
    }

    /**
     * 获取机器人账号头像地址
     * */
    public String getBotHeadPic(){
        params = new HashMap<>();
        params.put("robot_wxid",robotId);
        params.put("type",GET_ROBOT_HEAD_PIC);
        JSONObject obj = JSONUtil.parseObj(params);
        String pic = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString())).getStr("data");
        return pic;
    }

    /**
     * 获取登录账号列表
     * */
    public JSONArray getBotList(){
        params = new HashMap<>();
        params.put("type",GET_ROBOT_LIST);
        JSONObject obj = JSONUtil.parseObj(params);
        obj = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString()));
        JSONArray bots = null;
        try {
            bots = JSONUtil.parseArray(URLDecoder.decode(obj.getStr("data"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bots;
    }

    /**
     * 获取好友列表
     * @param robotId 机器人账户id 不指定则取所有机器人好友列表<br>
     * @param isRefresh 是否刷新缓存
     * */
    public JSONArray getFriendList(String robotId, boolean isRefresh){
        params = new HashMap<>();
        params.put("type",GET_ROBOT_FRIEND);
        params.put("robot_wxid",robotId==null?"":robotId);
        params.put("is_refresh",isRefresh?0:1);
        JSONObject obj = JSONUtil.parseObj(params);
        obj = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString()));
        JSONArray friends = null;
        try {
            friends = JSONUtil.parseArray(URLDecoder.decode(obj.getStr("data"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return friends;
    }

    /**
     * 获取群聊列表
     * @param robotId 机器人账户id 不指定则取所有机器人群聊列表<br>
     * @param isRefresh 是否刷新缓存
     * */
    public JSONArray getGroupList(String robotId, boolean isRefresh){
        params = new HashMap<>();
        params.put("type",GET_ROBOT_GROUP);
        params.put("robot_wxid",robotId==null?"":robotId);
        params.put("is_refresh",isRefresh?0:1);
        JSONObject obj = JSONUtil.parseObj(params);
        obj = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString()));
        JSONArray groups = null;
        try {
            groups = JSONUtil.parseArray(URLDecoder.decode(obj.getStr("data"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return groups;
    }

    /**
     * 获取群成员列表
     * @param groupId 群聊id<br>
     * @param isRefresh 是否刷新缓存
     * */
    public JSONArray getGroupMemberList(String groupId, boolean isRefresh){
        params = new HashMap<>();
        params.put("type",GET_ROBOT_GROUP_MEMBER_LIST);
        params.put("robot_wxid",robotId);
        params.put("group_wxid",groupId);
        params.put("is_refresh",isRefresh?0:1);
        JSONObject obj = JSONUtil.parseObj(params);
        obj = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString()));
        JSONArray list = null;
        try {
            list = JSONUtil.parseArray(URLDecoder.decode(obj.getStr("data"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 取群成员信息
     * */
    public JSONObject getGroupMember(String groupId,String userId){
        params = new HashMap<>();
        params.put("type",GET_ROBOT_GROUP_MEMBER);
        params.put("robot_wxid",robotId);
        params.put("group_wxid",groupId);
        params.put("member_wxid",userId);
        JSONObject obj = JSONUtil.parseObj(params);
        obj = JSONUtil.parseObj(HttpUtil.post(domain, obj.toString())).getJSONObject("data");
        return obj;
    }

    /**
     * 修改好友备注
     * */
    public void modifyFriendNote(String userId,String note){
        params = new HashMap<>();
        params.put("type",MODIFY_FRIEND_NOTE);
        params.put("robot_wxid",robotId);
        params.put("friend_wxid",userId);
        params.put("note",note);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 删除好友
     * */
    public void deleteFriend(String userId){
        params = new HashMap<>();
        params.put("type",DELETE_FRIEND);
        params.put("robot_wxid",robotId);
        params.put("friend_wxid",userId);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 移出群成员
     * */
    public void  removeGroupMember(String groupId,String userId){
        params = new HashMap<>();
        params.put("type",REMOVE_GROUP_MEMBER);
        params.put("robot_wxid",robotId);
        params.put("group_wxid",groupId);
        params.put("member_wxid",userId);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 修改群名称
     * */
    public void  modifyGroupName(String groupId,String groupName){
        params = new HashMap<>();
        params.put("type",MODIFYY_GROUP_NAME);
        params.put("robot_wxid",robotId);
        params.put("group_wxid",groupId);
        try {
            params.put("group_name",URLEncoder.encode(groupName,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 修改群公告
     * */
    public void modifyGroupNotice(String groupId, String notice){
        params = new HashMap<>();
        params.put("type",MODIFYY_GROUP_NOTICE);
        params.put("robot_wxid",robotId);
        params.put("group_wxid",groupId);
        try {
            params.put("notice",URLEncoder.encode(notice,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 退出群聊
     * */
    public void quitGroup(String groupId){
        params = new HashMap<>();
        params.put("type",QUIT_GROUP);
        params.put("robot_wxid",robotId);
        params.put("group_wxid",groupId);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 邀请加入群聊
     * */
    public void inviteInGroup(String groupId, String userId){
        params = new HashMap<>();
        params.put("type",INVITE_IN_GROUP);
        params.put("robot_wxid",robotId);
        params.put("friend_wxid",userId);
        params.put("group_wxid",groupId);
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }

    /**
     * 同意加好友请求
     * */
    public void agreeFriendVerify(FriendAddEvent event){
        params = new HashMap<>();
        params.put("type",AGREE_FRIEND_VERIFY);
        params.put("robot_wxid",robotId);
        params.put("msg",event.getEventMsg());
        JSONObject obj = JSONUtil.parseObj(params);
        HttpUtil.post(domain, obj.toString());
    }
}
