package com.diandong.domain.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname UserCheckExportDTO
 * @Description 用户审核导出实体类
 * @Date 2022/5/24 10:51
 * @Created by YuLiu
 */
@Data
@ApiModel(value = "用户审核导出实体类")
public class UserCheckExportDTO implements Serializable {

    private static final long serialVersionUID = -2832576768777943237L;

    @Excel(name = "用户类型", readConverterExp = "00=系统用户")
    @ApiModelProperty(value = "用户类型")
    private String userType;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String nickName;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "状态", readConverterExp = "0=审核中,1=审核通过,2=审核失败")
    @ApiModelProperty(value = "审核状态")
    private String checkState;
}
