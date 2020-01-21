package com.ct.websocket.controller;

import com.ct.websocket.common.util.R;
import com.ct.websocket.common.util.validateUtils.ValidMessage;
import com.ct.websocket.entity.dto.Message;
import com.ct.websocket.service.IWebsocketCommunication;
import com.ct.websocket.service.WebSocketServer;
import jdk.internal.util.xml.impl.Input;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;

/**
 * @auther:chent69
 * @date: 2020/1/15-13 :47
 */
@RestController
@RequestMapping("/communication")
public class WebsocketCommunicationController {
    @Resource
    private IWebsocketCommunication communication;

    @PostMapping("sendMsg")
    public R sendMsg(@RequestBody @Validated(ValidMessage.class) Message msg){
        String messageId = communication.sendMsg(msg);
        if(StringUtils.isEmpty(messageId)){
            return R.error();
        }
        return R.ok().put("messageId",messageId).put("thread" ,Thread.currentThread().getId());
    }
}
