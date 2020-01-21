package com.ct.websocket.entity.chat;

import lombok.Data;
import org.springframework.web.util.UriBuilder;

import java.io.Serializable;

/**
 * @auther:chent69
 * @date: 2020/1/21-09 :47
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String from;

    private String to;

    private String text;

    private String imageUrl;

    public Message(){
    }
//    public Message(String from, String to, String text, String imageUrl){
//        this.from = from;
//        this.to = to;
//        this.text = text;
//        this.imageUrl = imageUrl;
//    }
}
