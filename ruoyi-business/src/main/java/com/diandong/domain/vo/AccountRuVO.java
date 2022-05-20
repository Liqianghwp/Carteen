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
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Data
@ApiModel("VO实体类")
public class AccountRuVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账单父级id
     */
    @ApiModelProperty(value = "账单父级id")
    private Long accountBookId;

    /**
     * 入库编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "入库编号")
    private Long id;

    /**
     * 入库时间
     */
    @ApiModelProperty(value = "入库时间")
    private LocalDateTime storageTime;

    /**
     * 供应商
     */
    @ApiModelProperty(value = "供应商")
    private String supplier;

    /**
     * 入库数量
     */
    @ApiModelProperty(value = "入库数量")
    private Long inventoryQuantity;

    /**
     * 入库方式
     */
    @ApiModelProperty(value = "入库方式")
    private Long aggregate;

    /**
     * 是否记账 0:是,1:否
     */
    @ApiModelProperty(value = "是否记账 0:是,1:否")
    private String tally;

    /**
     * 入库人
     */
    @ApiModelProperty(value = "入库人")
    private String warehousePeople;

    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private Long aggregateAmount;

    /**
     * 采购单号
     */
    @ApiModelProperty(value = "采购单号")
    private String purchaseOrderNumber;

    /**
     * 入库照片
     */
    @ApiModelProperty(value = "入库照片")
    private String pictureRu;


}