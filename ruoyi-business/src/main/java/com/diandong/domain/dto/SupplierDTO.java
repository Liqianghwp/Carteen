package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 供应商管理DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@Data
@ApiModel("供应商管理DTO实体类")
public class SupplierDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 账号信息
     */
    @ApiModelProperty(value = "账号信息")
    private String account;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    /**
     * 是否是黑名单 0：否，1：是
     */
    @ApiModelProperty(value = "是否是黑名单 0：否，1：是")
    private String isBlack;

    /**
     * 移入黑名单时间
     */
    @ApiModelProperty(value = "移入黑名单时间")
    private LocalDateTime moveTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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