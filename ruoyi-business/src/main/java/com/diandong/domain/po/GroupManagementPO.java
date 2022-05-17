package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 集团管理PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_group_management")
@Data
@ApiModel("集团管理PO实体类")
@Accessors(chain = true)
public class GroupManagementPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 集团名称
     */
    @TableField(value = "group_name")
    @ApiModelProperty(value = "集团名称")
    private String groupName;

    /**
     * 联系人名称
     */
    @TableField(value = "content_name")
    @ApiModelProperty(value = "联系人名称")
    private String contentName;

    /**
     * 联系人手机号
     */
    @TableField(value = "content_phone")
    @ApiModelProperty(value = "联系人手机号")
    private String contentPhone;

    /**
     * 允许添加食堂个数
     */
    @TableField(value = "canteens_allowed")
    @ApiModelProperty(value = "允许添加食堂个数")
    private Integer canteensAllowed;

    /**
     * 集团地址 这个地方是不是经纬度设置
     */
    @TableField(value = "group_address")
    @ApiModelProperty(value = "集团地址 这个地方是不是经纬度设置")
    private String groupAddress;

    /**
     * 营业执照 图片文件地址
     */
    @TableField(value = "business_license")
    @ApiModelProperty(value = "营业执照 图片文件地址")
    private String businessLicense;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 部门id（关联若依部门id）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "若依部门id")
    private Long deptId;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}