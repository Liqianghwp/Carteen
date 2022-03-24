package com.ruoyi.system.cache;

import com.ruoyi.common.constant.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author lingzhi
 * @date 2022/2/24
 */
@Component("codeValueCache")
public class CodeValueCache {
    @Resource
    private RedisTemplate redisTemplate;

    @Cacheable(value = Constants.CACHE_USER_KEY, key = "targetClass + methodName +#p0")
    public String getSysUserNickname(Long id) {
        HashMap userMap = (HashMap) redisTemplate.opsForHash().get(Constants.CACHE_USER_KEY, id.toString());
        if (Optional.ofNullable(userMap).isPresent()) {
            return (String) userMap.get("nickname");
        } else {
            return null;
        }
    }

    @CacheEvict(value = Constants.CACHE_USER_KEY, allEntries = true)
    public void deleteAll() {
    }
}
