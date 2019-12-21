package com.ct.websocket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ct.websocket.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author zsy
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device")
public class Device extends BaseEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备名
     */
    private String name;

    /**
     * 设备号
     */
    private String deviceId;

    /**
     * 通信协议--1.udp 2.tcp短连接 3.tcp长连接 4.mqtt 5.coap
     */
    private Integer communicationProtocol;

    /**
     * 采集频率（默认30s）
     */
    private Integer acquisitionFrequency;

    /**
     * 设备型号
     */
    private Integer type;

    /**
     * 0离线，1在线，2禁用
     */
    private Integer status;

    /**
     * 所属项目的id
     */
    private Long projectId;

    /**
     * 物模型key
     */
    private String tslKey;

    /**
     * 设备的经度
     */
    private BigDecimal longitude;

    /**
     * 设备的纬度
     */
    private BigDecimal latitude;

}
