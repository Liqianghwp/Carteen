package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品评价记录PO实体类
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@TableName("wis_dishes_evaluations_records")
@Data
@ApiModel("菜品评价记录PO实体类")
@Accessors(chain = true)
public class DishesEvaluationsRecordsPO implements Serializable {
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
     * 好评数量
     */
    @TableField(value = "good_num")
    @ApiModelProperty(value = "好评数量")
    private Integer goodNum;

    /**
     * 差评数量
     */
    @TableField(value = "bad_num")
    @ApiModelProperty(value = "差评数量")
    private Integer badNum;

    /**
     * 好评度
     */
    @TableField(value = "praise")
    @ApiModelProperty(value = "好评度")
    private Double praise;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
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