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
 * 支付设置VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("支付设置VO实体类")
public class PaymentConfigVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 支付方式 包含但不限于（现金，微信，支付宝等等）
     */
    @ApiModelProperty(value = "支付方式 包含但不限于（现金，微信，支付宝等等）")
    private String paymentMethod;

    /**
     * 状态 默认为1。状态（0:停用；1:启用）
     */
    @ApiModelProperty(value = "状态 默认为1。状态（0:停用；1:启用）")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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


}