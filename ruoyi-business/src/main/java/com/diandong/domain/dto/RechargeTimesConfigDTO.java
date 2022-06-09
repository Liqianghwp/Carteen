package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值次数设置DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("充值次数设置DTO实体类")
public class RechargeTimesConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Long sex;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private LocalDateTime beginTime;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private Integer time;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 次数
     */
    @ApiModelProperty(value = "次数")
    private Integer times;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 二维码
     */
    @ApiModelProperty(value = "二维码")
    private String qrCode;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer state;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}