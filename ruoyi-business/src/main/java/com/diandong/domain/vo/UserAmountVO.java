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
 * 用户金额VO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Data
@ApiModel("用户金额VO实体类")
public class UserAmountVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    /**
     * 补贴
     */
    @ApiModelProperty(value = "补贴")
    private BigDecimal subsidy;

    /**
     * 次数
     */
    @ApiModelProperty(value = "次数")
    private Integer times;

    /**
     * 类型 0：使用金钱；1：使用次数
     */
    @ApiModelProperty(value = "类型 0：使用金钱；1：使用次数")
    private Integer type;


}