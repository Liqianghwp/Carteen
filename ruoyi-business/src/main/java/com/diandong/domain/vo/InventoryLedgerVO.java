package com.diandong.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import com.diandong.domain.po.InventoryLedgerPO;
import com.diandong.domain.po.RawMaterialPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * 库存台账VO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("库存台账VO实体类")
public class InventoryLedgerVO extends BaseEntity implements Serializable {
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
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

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
     * 最小库存数量
     */
    @ApiModelProperty(value = "最小库存数量")
    private Integer minStockWarning;

    /**
     * 最大库存数量
     */
    @ApiModelProperty(value = "最大库存数量")
    private Integer maxStockWarning;


    /**
     * 库存
     * */
    @ApiModelProperty(value = "库存")
    @TableField(exist = false)
    private Long repertory;


    @ApiModelProperty(value = "单价")
    @TableField(exist = false)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "总金额")
    @TableField(exist = false)
    private BigDecimal amount;

    /**
     * 入库
     */
    @ApiModelProperty(value = "入库信息")
    @TableField(exist = false)
    private List<InventoryInboundVO> storage;


    /**
     * 入库
     */
    @ApiModelProperty(value = "出库信息")
    @TableField(exist = false)
    private List<InventoryOutboundVO> stockRemoval;


}