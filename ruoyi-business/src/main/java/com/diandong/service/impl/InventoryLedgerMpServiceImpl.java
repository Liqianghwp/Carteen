package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.InventoryLedgerPO;
import com.diandong.mapper.InventoryLedgerMapper;
import com.diandong.service.InventoryLedgerMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 库存台账service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryLedgerMpServiceImpl extends CommonServiceImpl<InventoryLedgerMapper, InventoryLedgerPO>
        implements InventoryLedgerMpService {

}
