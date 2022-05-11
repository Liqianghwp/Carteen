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
 * 原材料信息 原材料信息VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("原材料信息 原材料信息VO实体类")
public class RawMaterialVO extends BaseEntity implements Serializable {
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
     * 食堂姓名
     */
    @ApiModelProperty(value = "食堂姓名")
    private String canteenName;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

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
     * 采购类型id
     */
    @ApiModelProperty(value = "采购类型id")
    private Long purchaseTypeId;

    /**
     * 采购类型名称
     */
    @ApiModelProperty(value = "采购类型名称")
    private String purchaseTypeName;

    /**
     * 预估进价
     */
    @ApiModelProperty(value = "预估进价")
    private BigDecimal prePrice;

    /**
     * 仓库id
     */
    @ApiModelProperty(value = "仓库id")
    private Long storehouseId;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String storehouseName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 （0:停用；1:启用）
     */
    @ApiModelProperty(value = "状态 （0:停用；1:启用）")
    private Integer status;


}