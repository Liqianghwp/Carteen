package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 缓存监控
 *
 * @author ruoyi
 */
@Api(value = "/monitor/cache", tags = {"缓存监控"})
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 缓存信息
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "缓存信息", notes = "缓存信息", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping()
    public BaseResult getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return BaseResult.success(result);
    }
}
