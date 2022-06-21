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
 * 支付记录VO实体类
 *
 * @author YuLiu
 * @date 2022-06-17
 */
@Data
@ApiModel("支付记录VO实体类")
public class PayRecordsVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String serialId;

    /**
     * 支付类型（0:订单；1：充次；2：充数）
     */
    @ApiModelProperty(value = "支付类型（0:订单；1：充次；2：充数）")
    private Integer type;

    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private Integer payFlag;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    /**
     * 支付人
     */
    @ApiModelProperty(value = "支付人")
    private Long userId;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private Integer payWay;

    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号")
    private String payNo;


}