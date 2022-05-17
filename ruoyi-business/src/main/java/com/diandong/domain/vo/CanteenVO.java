package com.diandong.domain.vo;

import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 食堂信息VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("食堂信息VO实体类")
public class CanteenVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @JsonSerialize(using = ToStringSerializer.class)
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
     * 启用状态（0:未启用;1:已启用）
     */
    @ApiModelProperty(value = "启用状态（0:未启用;1:已启用）")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 导出勾选id集合
     */
    @ApiModelProperty(value = "导出勾选id集合")
    private List<Long> ids;
}