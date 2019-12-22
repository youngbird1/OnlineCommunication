package com.ct.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ct.websocket.entity.dto.DeviceListDTO;
import com.ct.websocket.server.DeviceService;
import com.ct.websocket.server.WebSocketServer;
import com.fasterxml.jackson.databind.JsonSerializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @auther:chent69
 * @date: 2019/12/21-12 :00
 */
@RestController
@RequestMapping("test")
public class testController {
    @Autowired
    DeviceService deviceService;

    @RequestMapping("getAllDeviceStatus")
    DeviceListDTO getAllDeviceStatus(){
        return deviceService.getCurrentRoleAllDeviceStatus();
    }

    @RequestMapping("webSocketPush")
    void webSocketPush(){
        DeviceListDTO deviceListDTO = deviceService.getCurrentRoleAllDeviceStatus();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        for( WebSocketServer webSocket: WebSocketServer.webSocketMap.values()){
            try {
                webSocket.sendMessage(JSONObject.toJSONString(deviceListDTO,SerializerFeature.WriteDateUseDateFormat));
//                webSocket.sendMessage(deviceListDTO.toString());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
