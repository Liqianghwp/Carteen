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
 * 充值记录VO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Data
@ApiModel("充值记录VO实体类")
public class RechargeAmountRecordsVO extends BaseEntity implements Serializable {
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
    private String serialNumber;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private Long userId;

    /**
     * 充值类型(0:;1:;2:;3:;)
     */
    @ApiModelProperty(value = "充值类型(0:;1:;2:;3:;)")
    private String rechargeType;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    /**
     * 充值方式(0:;1:;2:;3:;)
     */
    @ApiModelProperty(value = "充值方式(0:;1:;2:;3:;)")
    private String rechargeMethod;

    /**
     * 状态 0：未取消；1：已取消
     */
    @ApiModelProperty(value = "状态 0：未取消；1：已取消")
    private Integer state;


}