package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.mapper.RawMaterialMapper;
import com.diandong.service.RawMaterialMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RawMaterialMpServiceImpl extends CommonServiceImpl<RawMaterialMapper, RawMaterialPO>
        implements RawMaterialMpService {

}
