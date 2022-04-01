package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.vo.OpinionFeedbackResponseVO;
import com.ruoyi.common.core.domain.BaseResult;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface OpinionFeedbackMpService extends CommonService<OpinionFeedbackPO> {


    /**
     * 根据食堂id查询意见列表
     *
     * @param canteenId 食堂id
     * @return BaseResult
     */
    List<OpinionFeedbackResponseVO> getPcOpinionList(Long canteenId);

    /**
     * 根据集团id查看意见列表
     * @param groupId
     * @return
     */
    List<OpinionFeedbackResponseVO> getGroupPcOpinionList(Long groupId);
}
