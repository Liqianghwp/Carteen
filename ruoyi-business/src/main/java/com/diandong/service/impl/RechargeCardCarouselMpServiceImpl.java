package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RechargeCardCarouselPO;
import com.diandong.mapper.RechargeCardCarouselMapper;
import com.diandong.service.RechargeCardCarouselMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 充值卡触摸屏轮播图service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeCardCarouselMpServiceImpl extends CommonServiceImpl<RechargeCardCarouselMapper, RechargeCardCarouselPO>
        implements RechargeCardCarouselMpService {

}
