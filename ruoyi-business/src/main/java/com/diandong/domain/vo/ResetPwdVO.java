package com.diandong.domain.vo;

import com.diandong.configuration.PhoneConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname ResetPwdVO
 * @Description 重设密码实体类
 * @Date 2022/6/13 13:52
 * @Created by YuLiu
 */
@Data
@ApiModel("找回密码实体类")
public class ResetPwdVO implements Serializable {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @PhoneConstraint
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

}
