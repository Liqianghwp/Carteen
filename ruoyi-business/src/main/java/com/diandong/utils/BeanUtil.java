package com.diandong.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.cache.CodeValueCache;

/**
 * @author lingzhi
 * @date 2022/2/24
 */
public class BeanUtil {
    /**
     * 获得SpringBean容器中的CodeValueCache
     *
     * @return
     */
    public static CodeValueCache getCodeValueCacheBean() {
        return SpringUtils.getBean(CodeValueCache.class);
    }
}
