package com.ct.websocket.common.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ct.websocket.entity.dto.DeviceListDTO;
import com.ct.websocket.server.WebSocketServer;
import com.ct.websocket.server.impl.DeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @OriginalAuthor:
 * @Author: ChenT
 * @Date: 2019/12/22 16:38
 */
@Slf4j
public class PushScheduleWork {

    private ScheduledExecutorService scheduledExecutorService;
    private  DeviceServiceImpl deviceService;
    private String webSocketServerSid;

    public PushScheduleWork(DeviceServiceImpl deviceService , String sid){
       this.deviceService = deviceService;
        webSocketServerSid = sid;
       scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public void start(){
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        final Runnable pusher = () -> {
                DeviceListDTO deviceListDTO = deviceService.getCurrentRoleAllDeviceStatus();
                try {
                    WebSocketServer.webSocketMap.get(webSocketServerSid).sendMessage(JSONObject.toJSONString(deviceListDTO, SerializerFeature.WriteDateUseDateFormat));
                }catch (IOException e){
                    log.info("webSocket IO 异常");
                    e.printStackTrace();
                }
        };
        scheduledExecutorService.scheduleAtFixedRate(pusher , 0,10, TimeUnit.SECONDS);
    }

    public void stop(){
        scheduledExecutorService.shutdown();
    }
}
