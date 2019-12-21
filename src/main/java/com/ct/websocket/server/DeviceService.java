package com.ct.websocket.server;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ct.websocket.entity.Device;
import com.ct.websocket.entity.dto.DeviceListDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsy
 * @since 2019-12-17
 */
public interface DeviceService extends IService<Device> {

    DeviceListDTO getCurrentRoleAllDeviceStatus();
}
