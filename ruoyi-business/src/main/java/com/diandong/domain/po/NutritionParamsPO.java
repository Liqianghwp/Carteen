package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 营养信息PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_nutrition_params")
@Data
@ApiModel("营养信息PO实体类")
@Accessors(chain = true)
public class NutritionParamsPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂编号
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂编号")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 营养名称
     */
    @TableField(value = "nutrition_name")
    @ApiModelProperty(value = "营养名称")
    private String nutritionName;

    /**
     * 单位
     */
    @TableField(value = "unit")
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 使用状态 （0:未使用；1:已使用）
     */
    @TableField(value = "be_used")
    @ApiModelProperty(value = "使用状态 （0:未使用；1:已使用）")
    private Integer beUsed;

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
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
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