package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@TableName("wis_recipe")
@Data
@ApiModel("菜谱PO实体类")
@Accessors(chain = true)
public class RecipePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食谱名称
     */
    @TableField(value = "recipe_name")
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 食谱日期
     */
    @TableField(value = "recipe_date")
    @ApiModelProperty(value = "食谱日期")
    private LocalDate recipeDate;

    /**
     * 添加方式id
     */
    @TableField(value = "add_way_id")
    @ApiModelProperty(value = "添加方式id")
    private Long addWayId;

    /**
     * 添加方式名称
     */
    @TableField(value = "add_way_name")
    @ApiModelProperty(value = "添加方式名称")
    private String addWayName;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private Integer status;

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
     * 更新人id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人id")
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