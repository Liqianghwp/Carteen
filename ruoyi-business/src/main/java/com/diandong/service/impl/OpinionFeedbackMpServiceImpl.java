package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.mapper.OpinionFeedbackMapper;
import com.diandong.service.OpinionFeedbackMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpinionFeedbackMpServiceImpl extends CommonServiceImpl<OpinionFeedbackMapper, OpinionFeedbackPO>
        implements OpinionFeedbackMpService {

}
