package com.diandong.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author lingzhi
 * @date 2021/9/7
 */
@Slf4j
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        try {
            this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUsername());
            this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getUsername());
            this.strictInsertFill(metaObject, "userId", Long.class, SecurityUtils.getUserId());
        } catch (Exception e) {
        }
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        try {
            this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getUsername());
        } catch (Exception e) {
        }
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }
}
