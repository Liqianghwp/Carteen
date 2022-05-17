package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.OpinionFeedbackPO;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface OpinionFeedbackMpService extends CommonService<OpinionFeedbackPO> {

    /**
     * 重新设置意见列表
     * @param list  意见列表
     */
    void resetOpinionFeedBack(List<OpinionFeedbackPO> list);

}
