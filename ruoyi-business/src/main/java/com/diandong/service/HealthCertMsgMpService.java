package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.HealthCertMsgPO;

/**
 * 健康证到期消息Service接口类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
public interface HealthCertMsgMpService extends CommonService<HealthCertMsgPO> {

    /**
     * 已读信息
     * @param id
     *
     */
    /**
     * 已读信息
     * @param id    已读id
     * @return  {@link HealthCertMsgPO}
     */
    HealthCertMsgPO haveRead(Long id);

}
