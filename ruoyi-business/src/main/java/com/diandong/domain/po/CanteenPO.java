package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.diandong.configuration.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@TableName("wis_canteen")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class CanteenPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 联系人名称
     */
    @TableField(value = "content_name")
    @ApiModelProperty(value = "联系人名称")
    private String contentName;

    /**
     * 联系人手机号码
     */
    @TableField(value = "content_phone")
    @ApiModelProperty(value = "联系人手机号码")
    private String contentPhone;

    /**
     * 食堂地址 这个地方是否是经纬度
     */
    @TableField(value = "canteen_address")
    @ApiModelProperty(value = "食堂地址 这个地方是否是经纬度")
    private String canteenAddress;

    /**
     * 营业执照 营业执照路径地址
     */
    @TableField(value = "business_license")
    @ApiModelProperty(value = "营业执照 营业执照路径地址")
    private String businessLicense;

    /**
     * 二维码 二维码路径地址
     */
    @TableField(value = "qr_code")
    @ApiModelProperty(value = "二维码 二维码路径地址")
    private String qrCode;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 食堂图片 存储的是路径地址
     */
    @TableField(value = "canteen_picture")
    @ApiModelProperty(value = "食堂图片 存储的是路径地址")
    private String canteenPicture;

    /**
     * 食堂文本介绍
     */
    @TableField(value = "canteen_introduce")
    @ApiModelProperty(value = "食堂文本介绍")
    private String canteenIntroduce;

    /**
     * 集团或部门的id
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "集团或部门的id")
    private Long pId;

    /**
     * 集团或部门名称
     */
    @TableField(value = "p_name")
    @ApiModelProperty(value = "集团或部门名称")
    private String pName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_name")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新者姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新者姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}