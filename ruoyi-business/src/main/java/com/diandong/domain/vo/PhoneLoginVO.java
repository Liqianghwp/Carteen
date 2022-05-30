package com.diandong.domain.vo;

import com.diandong.configuration.PhoneConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname PhoneLoginVO
 * @Description 手机验证码登录&注册
 * @Date 2022/5/26 10:07
 * @Created by YuLiu
 */
@ApiModel(value = "手机号码验证码登录/注册实体类")
@Data
public class PhoneLoginVO implements Serializable {
    private static final long serialVersionUID = -3093314205549450910L;

    @NotBlank(message = "手机号不能为空")
    @PhoneConstraint
    @ApiModelProperty(value = "手机号")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "密码")
    private String password;
}
