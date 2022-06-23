package com.diandong.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 采购计划单详情VO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Data
@ApiModel("采购计划单详情VO实体类")
public class PurchasePlanDetailVO extends BaseEntity implements Serializable {
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
     * 采购计划单id
     */
    @ApiModelProperty(value = "采购计划单id")
    private String planId;

    /**
     * 原材料类别id
     */
    @ApiModelProperty(value = "原材料类别id")
    private Long categoryId;

    /**
     * 原材料类别名称
     */
    @ApiModelProperty(value = "原材料类别名称")
    private String categoryName;

    /**
     * 原材料id
     */
    @ApiModelProperty(value = "原材料id")
    private Long rawMaterialId;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id")
    private Long unitId;

    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 上次进价
     */
    @ApiModelProperty(value = "上次进价")
    private BigDecimal lastPurchase;

    /**
     * 采购数量
     */
    @ApiModelProperty(value = "采购数量")
    private Double number;

    /**
     * 询问单价
     */
    @ApiModelProperty(value = "询问单价")
    private BigDecimal currentPrice;


}