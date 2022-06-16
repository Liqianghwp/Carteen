package com.ruoyi.web.controller.business;

import com.diandong.constant.Constants;
import com.diandong.domain.vo.AppLoginVO;
import com.diandong.domain.vo.PhoneRegisterAndLoginVO;
import com.diandong.domain.vo.ResetPwdVO;
import com.diandong.domain.vo.SysUserVO;
import com.diandong.service.AppService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname AppController
 * @Description App相关接口信息
 * @Date 2022/5/25 15:24
 * @Created by YuLiu
 */

@Validated
@RestController
@Api(value = "/app", tags = {"APP相关接口"})
@RequestMapping(value = "/app")
public class AppController extends BaseController {
    @Resource
    private AppService appService;


//    1、获取验证码

    @ApiOperation(value = "发送短信验证码")
    @GetMapping("/phoneMessage")
    public BaseResult getPhoneMessage(String phone) {
        appService.getPhoneVerifyCode(phone);
        return BaseResult.success();
    }

    //    2、注册&登录
    @ApiOperation(value = "手机号验证码注册后登录")
    @PostMapping("/phoneRegister")
    public BaseResult phoneRegisterAndLogin(@RequestBody @Validated PhoneRegisterAndLoginVO phoneLogin) {
        return appService.phoneRegisterAndLogin(phoneLogin);
    }

    @ApiOperation(value = "APP账密登录")
    @PostMapping("/appLogin")
    public BaseResult appLogin(@RequestBody @Validated AppLoginVO vo) {
        return appService.appLogin(vo);
    }



    //    4、更换手机号
    @ApiOperation("用户修改手机号-原手机号-获取验证码")
    @GetMapping("/change/old_phone_message")
    public BaseResult getChangePhoneMessage() {
        String oldPhone = SecurityUtils.getLoginUser().getUser().getPhonenumber();
        return appService.getChangePhoneVerifyCode(oldPhone, Constants.OLD_PHONE_VERIFY_CODE);
    }

    @ApiOperation("用户修改手机号-新手机号-获取验证码")
    @GetMapping("/change/new_phone_message")
    public BaseResult getChangeNewPhoneMessage(String phone) {
        return appService.getChangePhoneVerifyCode(phone, Constants.OLD_PHONE_VERIFY_CODE);
    }

    @ApiOperation("用户修改手机号-验证码填写完成")
    @GetMapping("/changePhone")
    public BaseResult changePhone(String oldPhoneCode, String newPhone, String newPhoneCode) {
        return appService.changePhone(oldPhoneCode, newPhone, newPhoneCode);
    }

    /**
     * 找回密码：发送验证码
     *
     * @param vo 找回密码实体
     * @return
     */
    @ApiOperation(value = "找回密码:发送验证码", notes = "发送验证码", httpMethod = "PUT")
    @PutMapping("/reset/get_code")
    public BaseResult getResetPwdVerifyCode(@RequestBody @Validated ResetPwdVO vo) {
        return appService.resetPwdVerifyCode(vo);
    }

    /**
     * 找回密码：校验验证码
     *
     * @param vo 找回密码实体
     * @return
     */
    @ApiOperation(value = "找回密码:校验验证码", notes = "校验验证码，验证码不能为空", httpMethod = "PUT")
    @PutMapping("/reset/verify_code")
    public BaseResult resetVerifyPhoneCode(@RequestBody @Validated ResetPwdVO vo) {
        if (StringUtils.isBlank(vo.getVerifyCode())) {
            return BaseResult.error("验证码不能为空");
        }

        return appService.resetVerifyPhoneCode(vo);
    }

    /**
     * 找回密码：设置新密码
     *
     * @param vo 找回密码实体
     * @return
     */
    @ApiOperation(value = "找回密码:设置新密码", notes = "设置新密码，密码不能为空")
    @PostMapping("/reset/pwd")
    public BaseResult resetPwd(@RequestBody @Validated ResetPwdVO vo) {
        if (StringUtils.isBlank(vo.getPassword())) {
            return BaseResult.error("密码不能为空");
        }
        return appService.resetPwd(vo);
    }
}


