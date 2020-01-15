package com.ct.websocket.controller;

import com.ct.websocket.common.util.R;
import com.ct.websocket.common.util.validateUtils.ValidMessage;
import com.ct.websocket.entity.dto.Message;
import com.ct.websocket.service.IWebsocketCommunication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
