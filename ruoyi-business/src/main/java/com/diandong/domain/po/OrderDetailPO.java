package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_order_detail")
@Data
@ApiModel("订单详情PO实体类")
@Accessors(chain = true)
public class OrderDetailPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 菜品id
     */
    @TableField(value = "dishes_id")
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

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
    private BigDecimal dishesPrice;

    /**
     * 菜品数量
     */
    @TableField(value = "dishes_count")
    @ApiModelProperty(value = "菜品数量")
    private Integer dishesCount;

    /**
     * 菜品总价
     */
    @TableField(value = "dishes_total_price")
    @ApiModelProperty(value = "菜品总价")
    private BigDecimal dishesTotalPrice;

    /**
     * 菜品图片 图片地址（后期如果没有了怎么办）
     */
    @TableField(value = "dishes_picture")
    @ApiModelProperty(value = "菜品图片 图片地址（后期如果没有了怎么办）")
    private String dishesPicture;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

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
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}