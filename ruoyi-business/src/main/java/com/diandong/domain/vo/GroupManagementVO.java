package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.naming.InsufficientResourcesException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Data
@ApiModel("集团管理VO实体类")
public class GroupManagementVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 集团名称
     */
    @NotEmpty(groups = {Insert.class}, message = "集团名称不能为空")
    @ApiModelProperty(value = "集团名称")
    private String groupName;

    /**
     * 联系人名称
     */
    @NotEmpty(groups = {Insert.class}, message = "联系人名称不能为空")
    @ApiModelProperty(value = "联系人名称")
    private String contentName;

    /**
     * 联系人手机号
     */
    @NotEmpty(groups = {Insert.class}, message = "联系人手机号不能为空")
    @ApiModelProperty(value = "联系人手机号")
    private String contentPhone;

    /**
     * 允许添加食堂个数
     */
    @NotNull(groups = {Insert.class}, message = "允许添加食堂个数不能为空")
    @ApiModelProperty(value = "允许添加食堂个数")
    private Integer canteensAllowed;

    /**
     * 集团地址 这个地方是不是经纬度设置
     */
    @ApiModelProperty(value = "集团地址 这个地方是不是经纬度设置")
    private String groupAddress;

    /**
     * 营业执照 图片文件地址
     */
    @ApiModelProperty(value = "营业执照 图片文件地址")
    private String businessLicense;

    /**
     * 状态
     */
    @NotNull(groups = {Insert.class}, message ="状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;


}