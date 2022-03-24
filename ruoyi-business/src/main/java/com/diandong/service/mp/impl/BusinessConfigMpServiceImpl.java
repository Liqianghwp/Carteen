package com.diandong.service.mp.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.mapper.BusinessConfigMapper;
import com.diandong.service.mp.BusinessConfigMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 系统配置service实现类
 *
 * @author YuLiu
 * @date 2022-03-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessConfigMpServiceImpl extends CommonServiceImpl<BusinessConfigMapper, BusinessConfigPO>
        implements BusinessConfigMpService {

}
