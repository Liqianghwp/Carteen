package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.CanteenCarouselPO;
import com.diandong.mapper.CanteenCarouselMapper;
import com.diandong.service.CanteenCarouselMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 食堂轮播图service实现类
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CanteenCarouselMpServiceImpl extends CommonServiceImpl<CanteenCarouselMapper, CanteenCarouselPO>
        implements CanteenCarouselMpService {

}
