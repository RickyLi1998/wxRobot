# wxRobot<br>

可爱猫4.0消息处理端
----
在plugin包下新建你的插件<br>
在application.yml中注册你的插件<br>
----
在使用前需要在bot中配置可爱猫的服务器地址<br>
目前bot的API支持<br>
```java
sendPrivateMessage()    // 发送私聊消息
sendGroupMessage()      // 发送群聊消息
sendImageMessage()      // 发送图片消息 可为网络图片
sendVideoMessage()      // 发送视频消息
sendFileMessage()       // 发送文件
sendEmojiMessage()      // 发送表情图
sendLinkMessage()       // 分享链接 中文的编码目前存在问题
sendMusicMessage()      // 点歌
```
目前支持的消息事件有
```java
onPrivateMessage()  //私聊事件
onGroupMessage()    //群聊事件
```
----
插件的开发需要继承 BotPlugin
```java
@Component
public class TestPlugin extends BotPlugin {
    // 根据需要去实现 BotPlugin 的事件 目前就只有两种事件
}
```
