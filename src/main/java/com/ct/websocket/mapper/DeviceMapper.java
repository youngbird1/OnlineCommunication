package com.ct.websocket.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ct.websocket.entity.Device;
import com.ct.websocket.entity.vo.DeviceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-12-17
 */
@Repository
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    List<DeviceVO> getAllDeviceByRole(@Param("roleName") String roleName);

}
