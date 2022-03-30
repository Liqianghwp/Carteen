package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@TableName("wis_dishes")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class DishesPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 菜品分类id
     */
    @TableField(value = "dishes_type_id")
    @ApiModelProperty(value = "菜品分类id")
    private Long dishesTypeId;

    /**
     * 菜品分类名称
     */
    @TableField(value = "dishes_type_name")
    @ApiModelProperty(value = "菜品分类名称")
    private String dishesTypeName;

    /**
     * 菜品名称
     */
    @TableField(value = "dishes_name")
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 菜品价格
     */
    @TableField(value = "dishes_price")
    @ApiModelProperty(value = "菜品价格")
    private Double dishesPrice;

    /**
     * 菜品单位
     */
    @TableField(value = "dishes_unit")
    @ApiModelProperty(value = "菜品单位")
    private String dishesUnit;

    /**
     * 规格
     */
    @TableField(value = "specification")
    @ApiModelProperty(value = "规格")
    private String specification;

    /**
     * 预估价
     */
    @TableField(value = "pre_price")
    @ApiModelProperty(value = "预估价")
    private Double prePrice;

    /**
     * 产地
     */
    @TableField(value = "origin")
    @ApiModelProperty(value = "产地")
    private String origin;

    /**
     * 菜品属性id
     */
    @TableField(value = "dishes_attr_id")
    @ApiModelProperty(value = "菜品属性id")
    private Long dishesAttrId;

    /**
     * 菜品属性名称
     */
    @TableField(value = "dishes_attr_name")
    @ApiModelProperty(value = "菜品属性名称")
    private String dishesAttrName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 菜品图片
     */
    @TableField(value = "dishes_picture")
    @ApiModelProperty(value = "菜品图片")
    private String dishesPicture;

    /**
     * 菜品介绍
     */
    @TableField(value = "dishes_introduction")
    @ApiModelProperty(value = "菜品介绍")
    private String dishesIntroduction;

    /**
     * 状态
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态")
    private Integer state;

    /**
     * 数据状态
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_name")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}