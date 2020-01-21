package com.ct.websocket.common.Exception;

/**
 * @auther:chent69
 * @date: 2020/1/19-09 :51
 */
public class MessageSaveFailedException extends Exception{
    private String detailMsg;
    public MessageSaveFailedException(String errMsg){
        detailMsg = errMsg;
    }
    @Override
    public String toString(){
        return "MessageSaveFailedException [thread:" + Thread.currentThread().getId() + "]: " + detailMsg;
    }
}
