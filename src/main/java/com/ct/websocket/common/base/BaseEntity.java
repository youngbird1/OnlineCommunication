package com.ct.websocket.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName :BaseEntity
 * @Description :所有entity的父类
 * @Date :2019/10/30 0030 14:19
 * @Author :zsy
 * @Version :
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 备用字段1
     */
    @TableField("test1")
    private String test1;
    /**
     * 备用字段2
     */
    @TableField("test2")
    private String test2;
    /**
     * 备用字段3
     */
    @TableField("test3")
    private String test3;
    /**
     * 备用字段4
     */
    @TableField("test4")
    private String test4;
}
