package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 采购订单管理VO实体类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Data
@ApiModel("采购订单管理VO实体类")
public class PurchaseOrderVO extends BaseEntity implements Serializable {
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
     * 采购订单号
     */
    @ApiModelProperty(value = "采购订单号")
    private String orderId;

    /**
     * 供应商id
     */
    @NotNull(groups = {Insert.class}, message = "供应商id不能为空")
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderPrice;

    /**
     * 状态 0：采购中；1：部分入库；2：入库成功
     */
    @ApiModelProperty(value = "状态 0：采购中；1：部分入库；2：入库成功")
    private Integer state;

    /**
     * 采购清单id集合
     */
    @NotBlank(groups = {Insert.class}, message = "采购清单id集合不能为空")
    @ApiModelProperty(value = "采购清单id集合")
    private String details;

    /**
     * 勾选要导出的id集合
     */
    @ApiModelProperty(value = "勾选导出的id集合")
    private List<Long> ids;


}