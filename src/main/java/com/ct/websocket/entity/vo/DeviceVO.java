package com.ct.websocket.entity.vo;

import lombok.Data;

/**
 * @auther:chent69
 * @date: 2019/12/20-17 :03
 */
@Data
public class DeviceVO {

    /**
     * 设备名
     */
    String deviceName;

    /**
     * 设备号
     */
    String deviceId;
    /**
     * 设备所属项目名
     */
    String projectName;

    /**
     * 0离线，1在线，2禁用
     */
    Integer status;
}
