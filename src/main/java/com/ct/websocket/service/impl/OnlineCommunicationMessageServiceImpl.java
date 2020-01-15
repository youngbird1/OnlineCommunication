package com.ct.websocket.service.impl;

import com.ct.websocket.entity.OnlineCommunicationMessage;
import com.ct.websocket.mapper.OnlineCommunicationMessageMapper;
import com.ct.websocket.service.IOnlineCommunicationMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ct
 * @since 2020-01-15
 */
@Service
public class OnlineCommunicationMessageServiceImpl extends ServiceImpl<OnlineCommunicationMessageMapper, OnlineCommunicationMessage> implements IOnlineCommunicationMessageService {

}
