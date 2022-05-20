package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@TableName("wis_account_ru")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class AccountRuPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账单父级id
     */
    @TableField(value = "account_book_id")
    @ApiModelProperty(value = "账单父级id")
    private Long accountBookId;

    /**
     * 入库编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "入库编号")
    private Long id;

    /**
     * 入库时间
     */
    @TableField(value = "storage_time")
    @ApiModelProperty(value = "入库时间")
    private LocalDateTime storageTime;

    /**
     * 供应商
     */
    @TableField(value = "supplier")
    @ApiModelProperty(value = "供应商")
    private String supplier;

    /**
     * 入库数量
     */
    @TableField(value = "inventory_quantity")
    @ApiModelProperty(value = "入库数量")
    private Long inventoryQuantity;

    /**
     * 入库方式
     */
    @TableField(value = "aggregate")
    @ApiModelProperty(value = "入库方式")
    private Long aggregate;

    /**
     * 是否记账 0:是,1:否
     */
    @TableField(value = "tally")
    @ApiModelProperty(value = "是否记账 0:是,1:否")
    private String tally;

    /**
     * 入库人
     */
    @TableField(value = "warehouse_people")
    @ApiModelProperty(value = "入库人")
    private String warehousePeople;

    /**
     * 总金额
     */
    @TableField(value = "aggregate_amount")
    @ApiModelProperty(value = "总金额")
    private Long aggregateAmount;

    /**
     * 采购单号
     */
    @TableField(value = "purchase_order_number")
    @ApiModelProperty(value = "采购单号")
    private String purchaseOrderNumber;

    /**
     * 入库照片
     */
    @TableField(value = "picture_ru")
    @ApiModelProperty(value = "入库照片")
    private String pictureRu;

}