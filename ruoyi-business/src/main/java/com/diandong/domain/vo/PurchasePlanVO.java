package com.diandong.domain.vo;

import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 采购计划单VO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Data
@ApiModel("采购计划单VO实体类")
public class PurchasePlanVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 采购计划单id
     */
    @ApiModelProperty(value = "采购计划单id")
    private String planId;

    /**
     * 审批单id
     */
    @ApiModelProperty(value = "审批单id")
    private String applyId;

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
     * 食谱日期
     */
    @ApiModelProperty(value = "食谱日期")
    private String recipeDate;

    /**
     * 总额
     */
    @ApiModelProperty(value = "总额")
    private BigDecimal total;

    /**
     * 审核状态 0：未提交；1：审核中；2：审核通过；3：审核驳回
     */
    @ApiModelProperty(value = "审核状态 0：未提交；1：审核中；2：审核通过；3：审核驳回")
    private Integer state;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 采购计划单详情记录
     */
    @ApiModelProperty(value = "采购计划单详情列表")
    private List<PurchasePlanDetailVO> purchasePlanDetailVOList;

}