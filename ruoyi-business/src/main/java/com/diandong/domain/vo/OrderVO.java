package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
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
 * @date 2022-03-30
 */
@Data
@ApiModel("订单VO实体类")
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂编号
     */
    @ApiModelProperty(value = "食堂编号")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    /**
     * 评价状态
     */
    @ApiModelProperty(value = "评价状态")
    private Integer evaluationStatus;

    /**
     * 支付方式id
     */
    @ApiModelProperty(value = "支付方式id")
    private Long paymentMethodId;

    /**
     * 支付方式名称
     */
    @ApiModelProperty(value = "支付方式名称")
    private String paymentMethodName;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;


}