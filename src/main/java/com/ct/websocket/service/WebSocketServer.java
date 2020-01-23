package com.ct.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ct.websocket.common.Exception.MessageSaveFailedException;
import com.ct.websocket.common.util.SensitiveWordUtil;
import com.ct.websocket.entity.OnlineCommunicationMessage;
import com.ct.websocket.entity.chat.ACK;
import com.ct.websocket.entity.chat.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * @auther:chent69
 * @date: 2019/12/16-16 :23
 */
@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    private static WebSocketServer webSocketServer;
    private  ExecutorService executorService;
    @Resource
    private IOnlineCommunicationMessageService messageService;

    //通过@PostConstruct实现初始化bean之前进行的操作
    @PostConstruct
    public void init(){
        webSocketServer = this;
//        // 初使化时将已静态化的testService实例化
//        webSocketServer.deviceService = this.deviceService;
        executorService = Executors.newCachedThreadPool();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全HashMap，用来存放每个客户端对应的WebSocket对象。
    public static ConcurrentHashMap<String , WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.sid = userId;
        //加入Map中
        webSocketMap.put(sid , this);
        //在线数加1
        addOnlineCount();
        log.info("有新窗口开始监听:{},当前在线人数为{}",sid ,getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从Map中删除
        webSocketMap.remove(this.sid);
        //在线数减1
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为{}" , getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param msg 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String msg) {
        log.info("收到来自窗口{}的信息:{}",sid ,msg);
        Message message = JSON.parseObject(msg,Message.class);
        //文本消息脱敏
        String text = message.getText();
        text = SensitiveWordUtil.replaceSensitiveWord(text,SensitiveWordUtil.maxMatchType,"*");
        message.setText(text);

        String to = message.getTo();
        //转发消息,将to字段设为空字符串以减少传输量
        message.setTo("");
        if(StringUtils.isEmpty(to)){
            //todo app发送的第一条消息，随机分配物业客服进行处理
            to = "1001002";
        }
        WebSocketServer webSocketServer = null;
        try {
            webSocketServer = webSocketMap.get(to);
            if(webSocketServer != null){
                //转发消息至目的地
                webSocketServer.
                        sendMessage(JSONObject.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
                //反馈消息接收状态给发送者
                sendMessage(JSONObject.toJSONString(new ACK(to,1, LocalDateTime.now()),
                            SerializerFeature.WriteDateUseDateFormat));
            }else{
                //反馈消息接收状态给发送者
                sendMessage(JSONObject.toJSONString(new ACK(to,0, LocalDateTime.now()),
                            SerializerFeature.WriteDateUseDateFormat));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo 保存消息至数据库
        {
//        String messageId = getMessageId();
//        if(webSocketServer != null){
//            msg.setIsRead(1);
//        }
//        executorService.execute(()->saveMessage(msg));
        }
    }

    /**
     *
     * @param error
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    private void saveMessage(OnlineCommunicationMessage communicationMessage){
        if(!webSocketServer.messageService.save(communicationMessage)){
            try{
                throw new MessageSaveFailedException("Message save to mysql failed!");
            }catch (MessageSaveFailedException e){
                e.printStackTrace();
            }
        }
    }
}
