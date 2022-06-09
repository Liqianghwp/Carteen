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
 * 充次记录VO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Data
@ApiModel("充次记录VO实体类")
public class RechargeTimesRecordsVO extends BaseEntity implements Serializable {
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
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 充值方式(0:;1:;2:;3:;)
     */
    @ApiModelProperty(value = "充值方式(0:;1:;2:;3:;)")
    private String rechargeMethod;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private LocalDateTime time;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    /**
     * 次数
     */
    @ApiModelProperty(value = "次数")
    private Integer count;

    /**
     * 剩余次数
     */
    @ApiModelProperty(value = "剩余次数")
    private Integer remainTimes;

    /**
     * 使用开始时间
     */
    @ApiModelProperty(value = "使用开始时间")
    private LocalDateTime startTime;

    /**
     * 使用结束时间
     */
    @ApiModelProperty(value = "使用结束时间")
    private LocalDateTime endTime;

    /**
     * 状态 1：已取消；0：未取消
     */
    @ApiModelProperty(value = "状态 1：已取消；0：未取消")
    private Integer state;


}