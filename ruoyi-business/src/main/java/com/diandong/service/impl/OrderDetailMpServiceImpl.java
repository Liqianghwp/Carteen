package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.OrderDetailPO;
import com.diandong.mapper.OrderDetailMapper;
import com.diandong.service.OrderDetailMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDetailMpServiceImpl extends CommonServiceImpl<OrderDetailMapper, OrderDetailPO>
        implements OrderDetailMpService {

}
