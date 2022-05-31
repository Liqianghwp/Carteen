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
 * 入库VO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("入库VO实体类")
public class InventoryInboundVO extends BaseEntity implements Serializable {
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
     * 入库人
     */
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
    @ApiModelProperty(value = "入库数量")
    private Integer number;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private Long price;

    /**
     * 入库方式id
     */
    @ApiModelProperty(value = "入库方式id")
    private Long inboundType;

    /**
     * 入库方式名称
     */
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


}