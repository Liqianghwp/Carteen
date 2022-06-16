package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.CommentPO;
import com.diandong.domain.vo.CommentVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface CommentMpService extends CommonService<CommentPO> {


    /**
     * 保存订单评价
     * @param vo
     * @return
     */
    BaseResult saveOrderEvaluation(CommentVO vo);

}
