package com.diandong.domain.vo;

import com.diandong.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Classname SysUserVO
 * @Description 用户管理入参实体类
 * @Date 2022/5/31 13:08
 * @Created by YuLiu
 */

@ApiModel(value = "用户管理入参实体类")
@Data
public class SysUserVO extends BaseEntity {


    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private String userType;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    /**
     * 用户所在区域
     */
    @ApiModelProperty(value = "用户所在区域")
    private String userArea;
    /**
     * 用户身高
     */
    @ApiModelProperty(value = "用户身高")
    private String userHeight;
    /**
     * 用户体重
     */
    @ApiModelProperty(value = "用户体重")
    private String userWeight;
    /**
     * 用户生日
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "用户生日")
    private LocalDate userBirthday;

    /**
     * 人脸图片
     */
    @ApiModelProperty(value = "人脸图片")
    private String faceImage;

    /**
     * 实体卡信息
     */
    @ApiModelProperty(value = "实体卡信息")
    private PhysicalCardVO physicalCard;

    /**
     * 健康指标信息
     */
    @ApiModelProperty(value = "健康指标信息")
    private List<HealthIndicatorsVO> healthIndicatorsList;

    /**
     * 营养建议
     */
    @ApiModelProperty(value = "营养建议")
    private List<UserNutritionAdviceVO> userNutritionAdviceList;

}
