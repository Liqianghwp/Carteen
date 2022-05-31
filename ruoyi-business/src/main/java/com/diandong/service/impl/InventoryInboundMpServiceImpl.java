package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.InventoryInboundPO;
import com.diandong.mapper.InventoryInboundMapper;
import com.diandong.service.InventoryInboundMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 入库service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryInboundMpServiceImpl extends CommonServiceImpl<InventoryInboundMapper, InventoryInboundPO>
        implements InventoryInboundMpService {

}
