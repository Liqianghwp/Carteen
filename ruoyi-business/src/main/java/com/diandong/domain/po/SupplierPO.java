package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 供应商管理PO实体类
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@TableName("wis_supplier")
@Data
@ApiModel("供应商管理PO实体类")
@Accessors(chain = true)
public class SupplierPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 供应商名称
     */
    @TableField(value = "supplier_name")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 账号信息
     */
    @TableField(value = "account")
    @ApiModelProperty(value = "账号信息")
    private String account;

    /**
     * 联系人姓名
     */
    @TableField(value = "contact_name")
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    /**
     * 联系人电话
     */
    @TableField(value = "contact_phone")
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    /**
     * 是否是黑名单 0：否，1：是
     */
    @TableField(value = "is_black")
    @ApiModelProperty(value = "是否是黑名单 0：否，1：是")
    private String isBlack;

    /**
     * 移入黑名单时间
     */
    @TableField(value = "move_time")
    @ApiModelProperty(value = "移入黑名单时间")
            private LocalDateTime moveTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否删除 0：否，1：是
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "是否删除 0：否，1：是")
    private Integer delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}