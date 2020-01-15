package com.ct.websocket.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ct.websocket.entity.OnlineCommunicationMessage;
import com.ct.websocket.entity.dto.Message;
import com.ct.websocket.service.IOnlineCommunicationMessageService;
import com.ct.websocket.service.IWebsocketCommunication;
import com.ct.websocket.service.WebSocketServer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * @auther:chent69
 * @date: 2020/1/15-14 :22
 */
@Service
public class WebsocketCommunicationImpl implements IWebsocketCommunication {
    @Resource
    IOnlineCommunicationMessageService communicationMessageService;
    @Override
    public String sendMsg(Message msg){
        //随机生成messageId
        //todo 需要保证messageId的唯一性
        String messageId = RandomStringUtils.randomAlphabetic(10);
        //与数据库对应的消息实体
        OnlineCommunicationMessage messageDO = new OnlineCommunicationMessage();
        BeanUtils.copyProperties(msg , messageDO);
        messageDO.setUkMessageId(messageId);

        msg.setIsDelete(0);
        msg.setIsRead(0);
        msg.setGmtCreate(LocalDateTime.now());
        msg.setMessageId(messageId);
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        String receiverUserId = msg.getReceiverUserid();
        if(StringUtils.isEmpty(receiverUserId)){
            //若发送给所有客服消息，将消息的ReceiveUserid设为"0"
            messageDO.setReceiverUserid("0");
            msg.setReceiverUserid("0");
            //todo receiveUserId为空时，用户发送消息给所有物业客服
            //todo 需要在维护websocket实例的webSocketMap中，遍历出所有物业在线账户，并发送数据
            for(Map.Entry<String,WebSocketServer> webSocketServerEntry : WebSocketServer.webSocketMap.entrySet()){
                //假设标示中含有"10010"的为物业客服
                if(webSocketServerEntry.getKey().contains("10010")){
                    try {
                        webSocketServerEntry.getValue().
                                sendMessage(JSONObject.toJSONString(msg, SerializerFeature.WriteDateUseDateFormat));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("遍历所有物业客服");
        }else {
            WebSocketServer webSocketServer = WebSocketServer.webSocketMap.get(receiverUserId);
            if(webSocketServer != null){
                try {
                    webSocketServer.sendMessage(JSONObject.toJSONString(msg, SerializerFeature.WriteDateUseDateFormat));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        //每一条消息存储到数据库
        communicationMessageService.save(messageDO);
        return messageId;
    }
}
