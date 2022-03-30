package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.OrderPO;
import com.diandong.mapper.OrderMapper;
import com.diandong.service.OrderMpService;
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
public class OrderMpServiceImpl extends CommonServiceImpl<OrderMapper, OrderPO>
        implements OrderMpService {

}
