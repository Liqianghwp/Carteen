package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname BackstageRechargeVO
 * @Description 后台充值记录
 * @Date 2022/6/6 14:59
 * @Created by YuLiu
 */
@ApiModel(value = "后台充值实体类")

@Data
public class BackstageRechargeVO implements Serializable {
    private static final long serialVersionUID = -5019914474251228590L;

    @NotNull(groups = {Insert.class},message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "充值次数（仅限当月）")
    private Integer times;
}
