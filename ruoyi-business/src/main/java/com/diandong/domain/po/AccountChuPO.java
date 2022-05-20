package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@TableName("wis_account_chu")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class AccountChuPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 出库数量
     */
    @TableField(value = "stock_removal")
    @ApiModelProperty(value = "出库数量")
    private Long stockRemoval;

    /**
     * 出库总金额
     */
    @TableField(value = "outbound_amoun")
    @ApiModelProperty(value = "出库总金额")
    private BigDecimal outboundAmoun;

    /**
     * 数量差额
     */
    @TableField(value = "difference")
    @ApiModelProperty(value = "数量差额")
    private Long difference;

    /**
     * 账单父级id
     */
    @TableField(value = "account_id")
    @ApiModelProperty(value = "账单父级id")
    private Long accountId;

    /**
     * 入库父级id
     */
    @TableField(value = "account_ru")
    @ApiModelProperty(value = "入库父级id")
    private Long accountRu;

    /**
     * 出库照片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "出库照片")
    private String picture;

    /**
     * 领取人
     */
    @TableField(value = "receiptor")
    @ApiModelProperty(value = "领取人")
    private String receiptor;

    /**
     * 出库方式
     */
    @TableField(value = "outbound way")
    @ApiModelProperty(value = "出库方式")
    private String outboundway;

    /**
     * 记账 0:是,1:否
     */
    @TableField(value = "tally")
    @ApiModelProperty(value = "记账 0:是,1:否")
    private String tally;

}