package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 食堂采购VO实体类
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@Data
@ApiModel("食堂采购VO实体类")
public class CanteenPurchaseVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @NotNull(groups = {Insert.class},message = "食谱开始日期不能为空")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "食谱日期开始时间")
    private LocalDate recipeStartDate;

    /**
     * 食谱日期结束时间
     */
    @NotNull(groups = {Insert.class},message = "食谱结束日期不能为空")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    @NotNull(message = "审核状态不能为空")
    @Max(value = 3, message = "请输入对应的审核状态")
    @Min(value = 0, message = "请输入对应的审核状态")
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


}