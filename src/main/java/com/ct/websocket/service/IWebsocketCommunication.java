package com.ct.websocket.service;

import com.ct.websocket.entity.dto.Message;

/**
 * @auther:chent69
 * @date: 2020/1/15-14 :22
 */
public interface IWebsocketCommunication {
    /**
     * 调用该接口，发送消息到指定用户
     * @param msg: 待发送的消息
     * @return messageId
     */
    String sendMsg(Message msg);
}
