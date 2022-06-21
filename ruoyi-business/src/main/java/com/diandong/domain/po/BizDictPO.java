package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务字典PO实体类
 *
 * @author YuLiu
 * @date 2022-06-10
 */
@TableName("wis_biz_dict")
@Data
@ApiModel("业务字典PO实体类")
@Accessors(chain = true)
public class BizDictPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 字典排序
     */
    @TableField(value = "dict_sort")
    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典名称
     */
    @TableField(value = "dict_label")
    @ApiModelProperty(value = "字典名称")
    private String dictLabel;

    /**
     * 字典值
     */
    @TableField(value = "dict_value")
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    /**
     * 停用启用状态 0：正常；1：停用
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "停用启用状态 0：正常；1：停用")
    private String status;

    /**
     * 集团id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "集团id")
    private Long groupId;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 餐次开始时间
     */
    @TableField(value = "begin_time")
    @ApiModelProperty(value = "餐次开始时间")
    private String beginTime;

    /**
     * 餐次结束时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value = "餐次结束时间")
    private String endTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 使用状态 0：未使用；1：使用
     */
    @TableField(value = "used")
    @ApiModelProperty(value = "使用状态 0：未使用；1：使用")
    private Integer used;

    /**
     * 删除状态 0：未删除；1：删除
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除状态 0：未删除；1：删除")
    private Integer delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}