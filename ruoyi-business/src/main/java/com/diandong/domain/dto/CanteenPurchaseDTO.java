package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 食堂采购DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@Data
@ApiModel("食堂采购DTO实体类")
public class CanteenPurchaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 食谱日期开始时间
     */
    @ApiModelProperty(value = "食谱日期开始时间")
    private LocalDate recipeStartDate;

    /**
     * 食谱日期结束时间
     */
    @ApiModelProperty(value = "食谱日期结束时间")
    private LocalDate recipeEndDate;

    /**
     * 有效日期
     */
    @ApiModelProperty(value = "有效日期")
    private String validDate;

    /**
     * 天数
     */
    @ApiModelProperty(value = "天数")
    private Integer days;

    /**
     * 审核状态 (0:未提交;1:审核中;2:审核通过;3:审核驳回;)
     */
    @ApiModelProperty(value = "审核状态 (0:未提交;1:审核中;2:审核通过;3:审核驳回;)")
    private Integer state;

    /**
     * 审核id
     */
    @ApiModelProperty(value = "审核id")
    private String applyId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 食谱列表
     */
    @ApiModelProperty(value = "食谱列表")
    private List<RecipeDTO> recipeList;

}