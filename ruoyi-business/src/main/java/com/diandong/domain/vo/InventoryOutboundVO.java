package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 出库VO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("出库VO实体类")
public class InventoryOutboundVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 库存台账id
     */
    @ApiModelProperty(value = "库存台账id")
    private Long ledgerId;

    /**
     * 领取人
     */
    @ApiModelProperty(value = "领取人")
    private String recipients;

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
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private Long number;

    /**
     * 出库方式id
     */
    @ApiModelProperty(value = "出库方式id")
    private Long outboundType;

    /**
     * 出库方式名称
     */
    @ApiModelProperty(value = "出库方式名称")
    private String outboundName;

    /**
     * 关联单号 这个可以根据不同的出库方式显示不同的单号
     */
    @ApiModelProperty(value = "关联单号 这个可以根据不同的出库方式显示不同的单号")
    private String association;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private Long price;

    /**
     * 原材料图片
     */
    @ApiModelProperty(value = "原材料图片")
    private String rawMaterialPic;

    /**
     * 是否记账 0：不记账（默认）；1：记账
     */
    @ApiModelProperty(value = "是否记账 0：不记账（默认）；1：记账")
    private String state;


}