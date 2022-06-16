package com.diandong.domain.vo;

import com.diandong.configuration.PhoneConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname AppLoginVO
 * @Description APP用户登录实体类
 * @Date 2022/6/13 11:31
 * @Created by YuLiu
 */
@Data
@ApiModel("APP用户登录实体类")
@NoArgsConstructor
@AllArgsConstructor
public class AppLoginVO implements Serializable {

    @ApiModelProperty(value = "账号")
    private String account;

    @PhoneConstraint
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码/密码", notes = "type=1:密码；type=2:验证码")
    @NotBlank(message = "密码或者验证码不能为空")
    private String authCode;

    @ApiModelProperty(value = "登录方式", notes = "1:账密登录；2：手机验证码登录")
    @NotBlank(message = "登录方式")
    private String type;

    public AppLoginVO(String phone, String type) {
        this.phone = phone;
        this.type = type;
    }
}
