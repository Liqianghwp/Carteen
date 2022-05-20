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
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Data
@ApiModel("VO实体类")
public class AccountVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别")
    private String category;

    /**
     * 原材料名
     */
    @ApiModelProperty(value = "原材料名")
    private String storageWay;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Long inventory;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    /**
     * 仓库
     */
    @ApiModelProperty(value = "仓库")
    private String warehouse;

    /**
     * 最小库存预警
     */
    @ApiModelProperty(value = "最小库存预警")
    private Long minimumInventoryWarning;

    /**
     * 最大库存预警
     */
    @ApiModelProperty(value = "最大库存预警")
    private Long minimumInventory;


}