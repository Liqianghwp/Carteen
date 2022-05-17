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
import java.util.List;

/**
 * 集团管理VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("集团管理VO实体类")
public class GroupManagementVO extends BaseEntity implements Serializable {
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
    @ApiModelProperty(value = "集团名称")
    private String groupName;

    /**
     * 联系人名称
     */
    @ApiModelProperty(value = "联系人名称")
    private String contentName;

    /**
     * 联系人手机号
     */
    @ApiModelProperty(value = "联系人手机号")
    private String contentPhone;

    /**
     * 允许添加食堂个数
     */
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
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 勾选导出的id集合
     */
    @ApiModelProperty(value = "勾选导出的id集合")
    private List<Long> ids;

}