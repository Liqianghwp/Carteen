package com.diandong.configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author Larry
 * @date 2022/1/24 0024 9:54
 * @description
 */
public class AliMessageUtil {

    /**
     * 手机验证部分配置
     */
    // 设置超时时间-可自行调整
    final static String defaultConnectTimeout  = "sun.net.client.defaultConnectTimeout";
    final static String defaultReadTimeout = "sun.net.client.defaultReadTimeout";
    final static String Timeout = "10000";
    // 初始化ascClient需要的几个参数
    final static String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
    final static String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
    // 替换成你的AK (产品密)
    final static String accessKeyId = "LTAI5tRdWzYPuYrukFR41QvM";// 你的accessKeyId,填你自己的 上文配置所得  自行配置
    final static String accessKeySecret = "EPkY9fVtQElea9CoAPdzLtsn0rIYjH";// 你的accessKeySecret,填你自己的 上文配置所得 自行配置
    // 必填:短信签名-可在短信控制台中找到
    final static String SignName = "北京思凯福"; // 阿里云配置你自己的短信签名填入
    // 必填:短信模板-可在短信控制台中找到
    final static String TemplateCode = "SMS_233126604"; // 阿里云配置你自己的短信模板CODE填入
    



    /**
     * 阿里云短信服务配置
     * @param mobile
     * @return
     */
    public static String getPhonemsg(String mobile) {

        /**
         * 进行正则关系校验
         */
        if (mobile == null || mobile == "") {
            return "";
        }
        /**
         * 短信验证---阿里大于工具
         */

        // 设置超时时间-可自行调整
        System.setProperty(defaultConnectTimeout, Timeout);
        System.setProperty(defaultReadTimeout, Timeout);
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
                    domain);
        } catch (ClientException e1) {
            e1.printStackTrace();
        }
        //获取验证码
        String code = vcode();
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SignName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TemplateCode);// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //本人的短信模板为   尊敬的用户，您的注册会员动态密码为：${code}，请勿泄漏于他人！  其中${code}为要生成的验证码
        request.setTemplateParam("{ \"code\":\""+code+"\"}");
        // 可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null
                    && sendSmsResponse.getCode().equals("OK")) {
                // 请求成功
            } else {
                //如果验证码出错，会输出错误码告诉你具体原因
                System.out.println(sendSmsResponse.getCode());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return "系统错误,请联系管理员";
        }
        return code;
    }

    /**
     * 生成6位随机数验证码
     * @return
     */
    public static String vcode(){
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }

   

}
