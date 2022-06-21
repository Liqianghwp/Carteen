package com.ruoyi.pay.util;

import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.util.Random;


/**
 * @author leiming
 * @Description
 * @date 15:56 2018/9/4
 **/
@Slf4j
public class RunningNumberUtil {

	private static String getProfiles(){
		Environment environment = SpringUtils.getBean(Environment.class);
		String profiles = environment.getProperty("spring.profiles.active");
		if("pro".equals(profiles)){
			return "";
		}
		return profiles+"-";
	}

    /**
     * description: 创建流水号
     *
     * @author: leiming
     * @date: 2018/9/4 16:11
     * @param: businessType 业务类型
     * @param: userId 用户ID
     * @return:
     */
    public static String createRunningNumber(String businessType, Long userId){
        String tRunningNum = getProfiles() + businessType + userId + System.currentTimeMillis();
        return tRunningNum;
    }

    public static String generateInviteCode(int len) {
        Assert.isTrue(len > 0, "长度要大于0");

        char[] chars = {'Q', 'W', 'E', '8', 'S', '2', 'D', 'Z',
                'X', '9', 'C', '7', 'P', '5', 'K', '3',
                'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y',
                'T', 'N', '6', 'B', 'G', 'H', 'A', 'L'};
        Random random = new Random();
        char[] inviteChars = new char[len];
        for (int i = 0; i < len; i++) {
            inviteChars[i] = chars[random.nextInt(chars.length)];
        }
        return String.valueOf(inviteChars);
    }

    /**
     * 创建流水号
     * @Author LinGQ
     * @DATE 2021/7/26 0026 16:37
     * @return : java.lang.String
     */
    public static String createRunningNumber(){
        return System.currentTimeMillis() + "";
    }

    /**
     * description: 创建订单号
     *
     * @author: leiming
     * @date: 2018/9/4 16:11
     * @param: businessType 业务类型
     * @param: userId 用户ID
     * @return:
     */
    public static String createOrderNumber(String businessType, Long userId){
        String tOrderNum = System.currentTimeMillis() +  businessType + userId;
        return tOrderNum;
    }

}
