package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.SupplierPO;
import com.diandong.mapper.SupplierMapper;
import com.diandong.service.SupplierMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 供应商管理service实现类
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierMpServiceImpl extends CommonServiceImpl<SupplierMapper, SupplierPO>
        implements SupplierMpService {

}
