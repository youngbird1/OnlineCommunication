package com.ct.websocket.entity.dto;


import com.ct.websocket.entity.vo.DeviceVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther:chent69
 * @date: 2019/12/20-15 :49
 */
@Data
public class DeviceListDTO implements Serializable {
    private static final long serialVersionUID = 1L;
//    /**
//     * 项目号
//     */
//    private Long projectId;

    /**
     * 设备在线数
     */
    private Long onLineCount;

    /**
     * 设备离线数
     */
    private Long offLineCount;

    /**
     * 设备禁用数
     */
    private Long disableCount;

    /**
     * 查询时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;

    /**
     * 设备信息
     */
    private List<DeviceVO> devices;
}
