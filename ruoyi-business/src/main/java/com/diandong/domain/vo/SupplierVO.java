package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.PhoneConstraint;
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
 * 供应商管理VO实体类
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@Data
@ApiModel("供应商管理VO实体类")
public class SupplierVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @PhoneConstraint
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
     * 导出
     * */
    @ApiModelProperty(value = "导出勾选id集合")
    private List<Long> ids;

}