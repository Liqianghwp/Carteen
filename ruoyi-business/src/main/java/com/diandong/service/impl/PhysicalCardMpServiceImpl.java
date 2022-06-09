package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.PhysicalCardPO;
import com.diandong.mapper.PhysicalCardMapper;
import com.diandong.service.PhysicalCardMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 实物卡service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PhysicalCardMpServiceImpl extends CommonServiceImpl<PhysicalCardMapper, PhysicalCardPO>
        implements PhysicalCardMpService {

}
