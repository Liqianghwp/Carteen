package com.diandong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname UserCheckVO
 * @Description 用户审核实体类
 * @Date 2022/5/24 10:16
 * @Created by YuLiu
 */
@Data
@ApiModel(value = "用户审核实体类")
public class UserCheckVO implements Serializable {
    private static final long serialVersionUID = 7912054292042204278L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 审核状态
     * 0：审核中，1：审核通过，2：驳回
     */
    @NotBlank(message = "审核状态不能为空")
    @Min(value = 0, message = "审核状态不正确")
    @Max(value = 2, message = "审核状态不正确")
    @ApiModelProperty(value = "审核状态(0=审核中（提交审核，审核失败后可以重新提交审核）1=审核通过，2=审核失败)", example = "1")
    private String checkState;

    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因", notes = "最大长度255字符，当审核失败时必传")
    private String rejectionReason;
}
