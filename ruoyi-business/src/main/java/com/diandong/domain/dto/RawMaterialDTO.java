package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 原材料信息 原材料信息DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("原材料信息 原材料信息DTO实体类")
public class RawMaterialDTO implements Serializable {
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
     * 食堂姓名
     */
    @ApiModelProperty(value = "食堂姓名")
    private String canteenName;

    /**
     * 原材料名称
     */
    @Excel(name = "原材料名称", sort = 2)
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
    @Excel(name = "原材料类别", sort = 1)
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
    @Excel(name = "单位", sort = 3)
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 采购类型id
     */
    @Excel(name = "采购类型", sort = 4, readConverterExp = "1=常采购,2=不常采购")
    @ApiModelProperty(value = "采购类型")
    private Integer purchaseType;

    /**
     * 预估进价
     */
    @Excel(name = "预估进价", sort = 5)
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

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @TableField(exist = false)
    @ApiModelProperty(value = "数量")
    private Double number;

    /**
     * 原材料营养信息集合
     */
    @ApiModelProperty(value = "原材料营养信息集合")
    private List<RawMaterialNutritionDTO> rawMaterialNutritionDTOList;

    @Excel(name = "库存", sort = 6)
    @ApiModelProperty(value = "库存")
    private Integer stock;
}