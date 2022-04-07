package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Data
@ApiModel("食堂DTO实体类")
public class CanteenDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 联系人名称
     */
    @ApiModelProperty(value = "联系人名称")
    private String contentName;

    /**
     * 联系人手机号码
     */
    @ApiModelProperty(value = "联系人手机号码")
    private String contentPhone;

    /**
     * 食堂地址 这个地方是否是经纬度
     */
    @ApiModelProperty(value = "食堂地址 这个地方是否是经纬度")
    private String canteenAddress;

    /**
     * 营业执照 营业执照路径地址
     */
    @ApiModelProperty(value = "营业执照 营业执照路径地址")
    private String businessLicense;

    /**
     * 二维码 二维码路径地址
     */
    @ApiModelProperty(value = "二维码 二维码路径地址")
    private String qrCode;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 食堂图片 存储的是路径地址
     */
    @ApiModelProperty(value = "食堂图片 存储的是路径地址")
    private String canteenPicture;

    /**
     * 食堂文本介绍
     */
    @ApiModelProperty(value = "食堂文本介绍")
    private String canteenIntroduce;

    /**
     * 集团或部门的id
     */
    @ApiModelProperty(value = "集团或部门的id")
    private Long pId;

    /**
     * 集团或部门名称
     */
    @ApiModelProperty(value = "集团或部门名称")
    private String pName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 默认为当前时间
     */
    @ApiModelProperty(value = "默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新者姓名
     */
    @ApiModelProperty(value = "更新者姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}