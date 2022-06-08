package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.TransferPO;
import com.diandong.mapper.TransferMapper;
import com.diandong.service.TransferMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TransferMpServiceImpl extends CommonServiceImpl<TransferMapper, TransferPO>
        implements TransferMpService {

}
