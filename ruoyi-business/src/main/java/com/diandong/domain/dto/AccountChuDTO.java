package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Data
@ApiModel("DTO实体类")
public class AccountChuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private Long stockRemoval;

    /**
     * 出库总金额
     */
    @ApiModelProperty(value = "出库总金额")
    private BigDecimal outboundAmoun;

    /**
     * 数量差额
     */
    @ApiModelProperty(value = "数量差额")
    private Long difference;

    /**
     * 账单父级id
     */
    @ApiModelProperty(value = "账单父级id")
    private Long accountId;

    /**
     * 入库父级id
     */
    @ApiModelProperty(value = "入库父级id")
    private Long accountRu;

    /**
     * 出库照片
     */
    @ApiModelProperty(value = "出库照片")
    private String picture;

    /**
     * 领取人
     */
    @ApiModelProperty(value = "领取人")
    private String receiptor;

    /**
     * 出库方式
     */
    @ApiModelProperty(value = "出库方式")
    private String outboundway;

    /**
     * 记账 0:是,1:否
     */
    @ApiModelProperty(value = "记账 0:是,1:否")
    private String tally;

}