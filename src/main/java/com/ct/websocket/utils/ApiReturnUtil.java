package com.ct.websocket.utils;

import com.ct.websocket.entity.ApiReturnObject;

/**
 * @auther:chent69
 * @date: 2019/12/16-16 :40
 */
public class ApiReturnUtil {

    public static  ApiReturnObject error(String msg){
        return new ApiReturnObject(msg , 500);
    }

    public static  ApiReturnObject success(String msg){
        return new ApiReturnObject(msg , 0);
    }
}
