package com.ct.websocket.entity.chat;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @auther:chent69
 * @date: 2020/1/21-10 :17
 */
@Data
public class ACK implements Serializable {
    private static final long serialVersionUID = 1L;

    private String to;

    private int isRead;

    private LocalDateTime nowTime;

    public ACK(String to, int isRead, LocalDateTime nowTime){
        this.to = to;
        this.isRead = isRead;
        this.nowTime = nowTime;
    }
}
