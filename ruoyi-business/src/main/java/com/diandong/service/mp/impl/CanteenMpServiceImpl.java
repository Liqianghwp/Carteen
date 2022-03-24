package com.diandong.service.mp.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.CanteenPO;
import com.diandong.mapper.CanteenMapper;
import com.diandong.service.mp.CanteenMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CanteenMpServiceImpl extends CommonServiceImpl<CanteenMapper, CanteenPO>
        implements CanteenMpService {

}
