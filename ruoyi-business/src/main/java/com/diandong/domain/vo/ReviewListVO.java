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
 * 审核列VO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Data
@ApiModel("审核列VO实体类")
public class ReviewListVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 业务id
     */
    @ApiModelProperty(value = "业务id")
    private Long businessId;

    /**
     * 审批单id
     */
    @ApiModelProperty(value = "审批单id")
    private String applyId;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private Integer applyStatus;

    /**
     * 审批步骤类型
     */
    @ApiModelProperty(value = "审批步骤类型")
    private Integer applyStepType;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 审批完成时间
     */
    @ApiModelProperty(value = "审批完成时间")
    private LocalDateTime finishTime;

    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    private String applyOpinion;

    /**
     * 审批顺序
     */
    @ApiModelProperty(value = "审批顺序")
    private Integer sort;


}