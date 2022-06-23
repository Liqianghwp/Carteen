package com.diandong.domain.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购订单管理DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Data
@ApiModel("采购订单管理DTO实体类")
public class PurchaseOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 集团id
     */
    @ApiModelProperty(value = "集团id")
    private Long groupId;

    /**
     * 采购订单号
     */
    @Excel(name = "采购订单号")
    @ApiModelProperty(value = "采购订单号")
    private String orderId;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 订单总金额
     */
    @Excel(name = "订单总金额")
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderPrice;

    /**
     * 状态 0：采购中；1：部分入库；2：入库成功
     */
    @Excel(name = "状态", readConverterExp = "0=采购中,1=部分入库,2=入库成功")
    @ApiModelProperty(value = "状态 0：采购中；1：部分入库；2：入库成功")
    private Integer state;

    /**
     * 采购清单id集合
     */
    @ApiModelProperty(value = "采购清单id集合")
    private String details;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;
    /**
     * 创建时间 默认为当前时间
     */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
     * 供应商联系人
     */
    @ApiModelProperty(value = "供应商联系人")
    private String supplierContact;

    /**
     * 供应商手机号
     */
    @ApiModelProperty(value = "供应商手机号")
    private String supplierPhone;

    /**
     * 采购详情信息
     */
    @ApiModelProperty(value = "采购详情信息")
    private List<PurchasingDTO> purchasingList;
}