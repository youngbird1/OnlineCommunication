package com.ct.websocket.server.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ct.websocket.entity.Device;
import com.ct.websocket.entity.dto.DeviceListDTO;
import com.ct.websocket.entity.vo.DeviceVO;
import com.ct.websocket.mapper.DeviceMapper;
import com.ct.websocket.server.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-12-17
 */
@Service
@Slf4j
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
//    @Autowired
//    HttpServletRequest httpServletRequest;
    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public DeviceListDTO getCurrentRoleAllDeviceStatus(){
        //todo 函数用时分析
        long s_time = System.currentTimeMillis();
        DeviceListDTO deviceDTO = new DeviceListDTO();
        String roleName = "系统管理员";
        //todo  定时推送的数据存在缓存、还是session、还是重新查询数据库?
        List<DeviceVO> deviceVOS = deviceMapper.getAllDeviceByRole(roleName);
        checkDeviceStatusWithRedisNewData(deviceVOS , deviceDTO);
        deviceDTO.setQueryTime(LocalDateTime.now());
        long e_time = System.currentTimeMillis();
        log.info("in getCurrentRoleAllDeviceStatus() usetime:{}ms",e_time - s_time);
        return deviceDTO;
    }

    //根据redis里有无该deviceId键值，确定是否在线
    private void checkDeviceStatusWithRedisNewData(List<DeviceVO> devices ,DeviceListDTO deviceDTO){
        List<String> deviceIdList = new ArrayList<>();
        Map<String ,DeviceVO> deviceMap = new HashMap<>();
        long onLineDeviceCount = 0;
        long offLineDeviceCount = 0;
        long disableDeviceCount = 0;
        long enableDeviceCount;
        for(DeviceVO device : devices) {
            //status 0:离线 1:在线 2:禁用
            if (device.getStatus() < 2) {
                deviceIdList.add(device.getDeviceId());
                deviceMap.put(device.getDeviceId() , device);
                //先统一默认设备不在线，后面根据redis中的newData判断设备的真实在线情况
                device.setStatus(0);
            }
        }
        enableDeviceCount = deviceIdList.size();
        disableDeviceCount = devices.size() - enableDeviceCount;
        Set<String> newDataSet = stringRedisTemplate.keys(  "newData::*" );
        if(newDataSet == null){
            return;
        }
        Iterator<String> it;
        String deviceId;
        for(String newData : newDataSet){
            it = deviceIdList.iterator();
            while (it.hasNext()){
                deviceId = it.next();
                if(newData.contains(deviceId)){
                    deviceMap.get(deviceId).setStatus(1);
                    onLineDeviceCount++;
                    it.remove();
                }
            }
        }
        offLineDeviceCount = enableDeviceCount - onLineDeviceCount;
        deviceDTO.setDevices(devices);
        deviceDTO.setDisableCount(disableDeviceCount);
        deviceDTO.setOffLineCount(offLineDeviceCount);
        deviceDTO.setOnLineCount(onLineDeviceCount);
    }
}
