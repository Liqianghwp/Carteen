package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 预留样品PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_reserve_sample")
@Data
@ApiModel("预留样品PO实体类")
@Accessors(chain = true)
public class ReserveSamplePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 留样日期
     */
    @TableField(value = "reserve_date")
    @ApiModelProperty(value = "留样日期")
    private LocalDateTime reserveDate;

    /**
     * 餐次
     */
    @TableField(value = "meal_times")
    @ApiModelProperty(value = "餐次")
    private Long mealTimes;

    /**
     * 食品名称
     */
    @TableField(value = "food_name")
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    /**
     * 留样数量
     */
    @TableField(value = "reserve_num")
    @ApiModelProperty(value = "留样数量")
    private String reserveNum;

    /**
     * 储存地点
     */
    @TableField(value = "storage_location")
    @ApiModelProperty(value = "储存地点")
    private Long storageLocation;

    /**
     * 留样人
     */
    @TableField(value = "reserve_name")
    @ApiModelProperty(value = "留样人")
    private String reserveName;

    /**
     * 预警周期
     */
    @TableField(value = "warning_day")
    @ApiModelProperty(value = "预警周期")
    private Integer warningDay;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 0：未处理-有效；1：未处理-报警；2：已处理
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态 0：未处理-有效；1：未处理-报警；2：已处理")
    private Integer state;

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