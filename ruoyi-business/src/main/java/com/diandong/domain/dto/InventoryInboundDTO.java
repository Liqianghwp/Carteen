package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入库DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("入库DTO实体类")
public class InventoryInboundDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 库存台账id
     */
    @ApiModelProperty(value = "库存台账id")
    private Long ledgerId;

    /**
     * 入库人
     */
    @Excel(name = "入库人",sort = 6)
    @ApiModelProperty(value = "入库人")
    private String inbounder;

    /**
     * 原材料图片
     */
    @ApiModelProperty(value = "原材料图片")
    private String rawMaterialPic;

    /**
     * 入库数量
     */
    @Excel(name = "入库数量",sort = 3)
    @ApiModelProperty(value = "入库数量")
    private Integer number;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 入库方式id
     */
    @ApiModelProperty(value = "入库方式id")
    private Long inboundType;

    /**
     * 入库方式名称
     */
    @Excel(name = "入库方式名称",sort = 5)
    @ApiModelProperty(value = "入库方式名称")
    private String inboundName;

    /**
     * 关联单号 这个跟入库方式相关
     */
    @ApiModelProperty(value = "关联单号 这个跟入库方式相关")
    private String association;

    /**
     * 仓库id
     */
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    /**
     * 是否记账 0：不记账；1：记账
     */
    @ApiModelProperty(value = "是否记账 0：不记账；1：记账")
    private String state;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @Excel(name = "入库时间",sort = 2)
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


    @TableField(exist = false)
    @Excel(name = "原材料名称",sort = 1)
    @ApiModelProperty(value = "原材料名称")
    private String rawName;


    @Excel(name = "总金额",sort = 4)
    @ApiModelProperty(value = "总金额")
    @TableField(exist = false)
    private BigDecimal amount;

}