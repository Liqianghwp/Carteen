//package com.ruoyi.web.controller.business;
//
//import com.diandong.configuration.AliMessageUtil;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.BaseResult;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Classname AppController
// * @Description App相关接口信息
// * @Date 2022/5/25 15:24
// * @Created by YuLiu
// */
//
//@Validated
//@RestController
//@Api(value = "/app", tags = {"APP相关接口"})
//@RequestMapping(value = "/app")
//public class AppController extends BaseController {
//
//    @Resource
//    private StringRedisTemplate redisTemplate;
////    1、获取验证码
//
//    @ApiOperation(value = "发送短信验证码")
//    @GetMapping("/phoneMessage")
//    public BaseResult getPhoneMessage(String phone) {
//        String phonemsg = AliMessageUtil.getPhonemsg(phone);
//        redisTemplate.opsForValue().set(phone, phonemsg, 120L, TimeUnit.SECONDS);
//        return BaseResult.success();
//    }
////    2、注册
////    3、登录
//
//    public BaseResult phoneLogin(){
//
//    }
//
////    4、更换手机号
//
//}
