package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 充值次数设置VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("充值次数设置VO实体类")
public class RechargeTimesVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 性别
     */
    @NotNull(groups = {Insert.class}, message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Long sex;

    /**
     * 时间
     */
    @NotBlank(groups = {Insert.class}, message = "时间不能为空")
    @ApiModelProperty(value = "时间")
    private String time;

    /**
     * 价格
     */
    @NotNull(groups = {Insert.class}, message = "价格不能为空")
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 次数
     */
    @NotNull(groups = {Insert.class}, message = "次数不能为空")
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
    @NotNull(groups = {Insert.class}, message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer state;


}