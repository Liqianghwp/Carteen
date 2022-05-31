package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.InventoryOutboundPO;
import com.diandong.mapper.InventoryOutboundMapper;
import com.diandong.service.InventoryOutboundMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 出库service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryOutboundMpServiceImpl extends CommonServiceImpl<InventoryOutboundMapper, InventoryOutboundPO>
        implements InventoryOutboundMpService {

}
