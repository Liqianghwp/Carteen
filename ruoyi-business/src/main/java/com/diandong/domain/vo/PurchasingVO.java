package com.diandong.domain.vo;

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
 * 采购清单管理VO实体类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Data
@ApiModel("采购清单管理VO实体类")
public class PurchasingVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 集团id
     */
    @ApiModelProperty(value = "集团id")
    private Long groupId;

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
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Double number;

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
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 是否采购 0：未采购；1：已采购
     */
    @ApiModelProperty(value = "是否采购 0：未采购；1：已采购")
    private Integer isPurchase;

    /**
     * 小计
     */
    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;


}