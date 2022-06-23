package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审核列DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Data
@ApiModel("审核列DTO实体类")
public class ReviewListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
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