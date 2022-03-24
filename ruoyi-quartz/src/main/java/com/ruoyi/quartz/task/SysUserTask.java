package com.ruoyi.quartz.task;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.cache.CodeValueCache;
import com.ruoyi.system.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存用户信息定时任务
 *
 * @author lingzhi
 * @date 2022/2/23
 */
@Component("sysUserTask")
@Slf4j
public class SysUserTask {

    @Resource
    private RedisCache redisCache;

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private CodeValueCache codeValueCache;

    public void cacheSysUser() {
        log.info("执行缓存用户信息定时任务");
        List<SysUser> sysUserList = userMapper.selectUserList(new SysUser());
        Map<String, HashMap> userMap = new HashMap();
        sysUserList.forEach(sysUser -> {
            HashMap map = new HashMap();
            map.put("id", sysUser.getUserId());
            map.put("name", sysUser.getUserName());
            map.put("nickname", sysUser.getNickName());
            map.put("dept", sysUser.getDept().getDeptName());
            userMap.put(sysUser.getUserId().toString(), map);
        });
        redisCache.setCacheMap(Constants.CACHE_USER_KEY, userMap);
        codeValueCache.deleteAll();
    }
}
