package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统配置 用于富文本设置PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_business_config")
@Data
@ApiModel("系统配置 用于富文本设置PO实体类")
@Accessors(chain = true)
public class BusinessConfigPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 配置名称
     */
    @TableField(value = "config_name")
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /**
     * 配置内容 text文本格式
     */
    @TableField(value = "config_value")
    @ApiModelProperty(value = "配置内容 text文本格式")
    private String configValue;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 数据状态 停用启用状态
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态 停用启用状态")
    private Integer dataState;

    /**
     * 0:false;1:true
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0:false;1:true")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}