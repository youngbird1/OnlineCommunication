package com.ct.websocket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ct
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OnlineCommunicationMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息表自增Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息发送方userId
     */
    private String senderUserid;

    /**
     * 消息接收方userId
     */
    private String receiverUserid;

    /**
     * 消息标示(唯一)
     */
    private String ukMessageId;

    /**
     * 消息体
     */
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

    /**
     * 预留字段1
     */
    private String test1;

    /**
     * 预留字段2
     */
    private String test2;

}
