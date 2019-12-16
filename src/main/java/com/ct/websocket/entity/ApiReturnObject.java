package com.ct.websocket.entity;

import lombok.Data;

/**
 * @auther:chent69
 * @date: 2019/12/16-16 :42
 */
@Data
public class ApiReturnObject {
    private String msg;
    private int code;

    public ApiReturnObject(String msg, int code){
        this.msg = msg;
        this.code = code;
    }

    public ApiReturnObject(){
    }
}
