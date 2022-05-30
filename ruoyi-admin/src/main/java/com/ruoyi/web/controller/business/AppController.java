package com.ruoyi.web.controller.business;

import com.diandong.configuration.AliMessageUtil;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.UserAndTokenDto;
import com.diandong.domain.vo.PhoneLoginVO;
import com.diandong.enums.UserTypeEnum;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.sms.SmsCodeAuthenticationToken;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;
//    1、获取验证码

    @ApiOperation(value = "发送短信验证码")
    @GetMapping("/phoneMessage")
    public BaseResult getPhoneMessage(String phone) {
        String phonemsg = AliMessageUtil.getPhonemsg(phone);
        redisTemplate.opsForValue().set(phone, phonemsg, 120L, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    //    2、注册
//    3、登录
    @ApiOperation(value = "手机号验证码登录&注册")
    @PostMapping("/phoneLogin")
    public BaseResult phoneLogin(@RequestBody @Validated PhoneLoginVO phoneLogin) {

        String phone = phoneLogin.getPhone();

//        获取验证码
        String realCode = redisTemplate.opsForValue().get(phone);
        if (!phoneLogin.getCode().equals(realCode)) {
            return BaseResult.error("验证码错误");
        }
        SysUser sysUser = sysUserService.lambdaQuery()
                .eq(SysUser::getPhonenumber, phone)
                .eq(SysUser::getDelFlag, String.valueOf(Constants.DEL_NO))
                .one();

        if (Objects.isNull(sysUser)) {
            sysUser = new SysUser();
            sysUser.setUserName(phone);
            sysUser.setPhonenumber(phone);
            sysUser.setNickName("用户" + phone);
            if (StringUtils.isNotBlank(phoneLogin.getPassword())) {
                sysUser.setPassword(SecurityUtils.encryptPassword(phoneLogin.getPassword()));
            }
            sysUser.setCreateTime(new Date());
            sysUser.setUserType(UserTypeEnum.DINERS.getValue());

        }
        UserAndTokenDto userAndTokenDto = loginWithPhoneCode(phone);
        return BaseResult.success(userAndTokenDto.getToken());
    }

//    4、更换手机号
    @ApiOperation("用户修改手机号-原手机号-获取验证码")
    @GetMapping("/change/old_phone_message")
    public BaseResult getChangePhoneMessage(String phone) {
        SysUser user = sysUserService.lambdaQuery().eq(SysUser::getPhonenumber, phone).one();
        if (user == null) {
            return BaseResult.error("用户不存在");
        }
        String code = AliMessageUtil.getPhonemsg(phone);
        redisTemplate.opsForValue().set(phone, code, 180L, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    @ApiOperation("用户修改手机号-新手机号-获取验证码")
    @GetMapping("/change/new_phone_message")
    public BaseResult getChangeNewPhoneMessage(String phone) {
        SysUser user = sysUserService.lambdaQuery().eq(SysUser::getPhonenumber, phone).one();
        if (user != null) {
            return BaseResult.error("新手机号已注册");
        }
        String code = AliMessageUtil.getPhonemsg(phone);
        redisTemplate.opsForValue().set(phone, code, 180L, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    @ApiOperation("用户修改手机号-验证码填写完成")
    @GetMapping("/changePhone")
    public BaseResult changePhone(String oldPhone, String oldPhoneCode, String newPhone, String newPhoneCode) {
        SysUser user = sysUserService.lambdaQuery().eq(SysUser::getPhonenumber, newPhone).one();
        if (user != null) {
            return BaseResult.error("手机号已注册");
        }
        String realCode = redisTemplate.opsForValue().get(oldPhoneCode);
        if (!oldPhoneCode.equals(realCode)) return BaseResult.error("原手机号验证码错误");
        realCode = redisTemplate.opsForValue().get(newPhoneCode);
        if (!newPhoneCode.equals(realCode)) return BaseResult.error("新手机号验证码错误");
        sysUserService.lambdaUpdate().eq(SysUser::getPhonenumber, oldPhone).set(SysUser::getPhonenumber, newPhone).update();
        return BaseResult.success(sysUserService.lambdaQuery().eq(SysUser::getPhonenumber, newPhone).one());
    }


    /**
     * 手机验证码登录
     *
     * @param phone
     * @return
     */
    private UserAndTokenDto loginWithPhoneCode(String phone) {
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
//            new Aut
            authentication = authenticationManager.authenticate(new SmsCodeAuthenticationToken(phone));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        String token = tokenService.createToken(loginUser);
        UserAndTokenDto userAndTokenDto = new UserAndTokenDto();
        userAndTokenDto.setToken(token);
        SysUser user = loginUser.getUser();
        user.setPassword(null);
        userAndTokenDto.setUser(user);
        return userAndTokenDto;
    }

}
