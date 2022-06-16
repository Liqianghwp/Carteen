package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 支付设置DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("支付设置DTO实体类")
public class PaymentConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 支付方式 包含但不限于（现金，微信，支付宝等等）
     */
    @ApiModelProperty(value = "支付方式 包含但不限于（现金，微信，支付宝等等）")
    private String paymentMethod;

    /**
     *支付方式的key
     */
    @ApiModelProperty(value = "key")
    private String payway;

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

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建日期 默认为当前时间
     */
    @ApiModelProperty(value = "创建日期 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}