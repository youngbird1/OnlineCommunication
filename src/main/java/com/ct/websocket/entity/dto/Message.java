package com.ct.websocket.entity.dto;

import com.ct.websocket.common.util.validateUtils.ValidMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @auther:chent69
 * @date: 2020/1/15-14 :05
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息发送方userId
     */
    @NotBlank(message = "send_userId为空" , groups = {ValidMessage.class})
    private String senderUserid;

    /**
     * 消息接收方userId
     */
    private String receiverUserid;

    /**
     * 消息标示(唯一)
     */
    private String messageId;

    /**
     * 消息体
     */
    @NotEmpty(message = "body消息体为空" , groups = {ValidMessage.class})
    private String body;

    /**
     * 消息的阅读状态(0:未读 1:已读)
     */
    private Integer isRead;

    /**
     * 消息的删除状态(0:未删 1:已删)
     */
    private Integer isDelete;

    /**
     * 消息创建时间(发送时间)
     */
    private LocalDateTime gmtCreate;
}
