package com.ruoyi.common.config;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname IdsKeyConfig
 * @Description TODO
 * @Date 2022/6/6 20:32
 * @Created by YuLiu
 */
@Configuration
public class IdsKeyConfig {
//    @Autowired
//    private static StringRedisTemplate redisTemplate;

    private static final String saveKey = "BIZ:IDS:";


    public static String getInboundNo() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        Long increment = redisTemplate.opsForValue().increment(saveKey + "inbound", 1L);

        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String value = StringUtils.leftPad(increment.toString(), 4, "0");
        String code = MessageFormat.format("{0}{1}", dateTime, value);

        return code;
    }


    public static void main(String[] args) {
//        IdsKeyConfig idsKey = new IdsKeyConfig();




        for (int i = 0; i < 100000; i++) {
            System.out.println(getInboundNo());
        }

    }
}
