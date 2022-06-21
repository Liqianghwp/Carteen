package com.diandong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SysConfigVO
 * @Description 系统配置入参实体
 * @Date 2022/5/18 17:06
 * @Created by YuLiu
 */
@Data
@ApiModel(value = "系统配置入参实体")
public class SysConfigVO implements Serializable {

    private static final long serialVersionUID = -2544042494116879647L;

    @ApiModelProperty(value = "注册开启关闭")
    private String registerUser;

    @ApiModelProperty(value = "证书到期")
    private String healthCertInvalid;

    @ApiModelProperty(value = "餐次间隔")
    private String mealTimesInterval;

    @ApiModelProperty(value = "餐次数量显示")
    private String meanTimesShow;


}
