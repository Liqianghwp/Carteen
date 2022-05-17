package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RawMaterialBlacklistPO;
import com.diandong.mapper.RawMaterialBlacklistMapper;
import com.diandong.service.RawMaterialBlacklistMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 原材料黑名单service实现类
 *
 * @author YuLiu
 * @date 2022-05-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RawMaterialBlacklistMpServiceImpl extends CommonServiceImpl<RawMaterialBlacklistMapper, RawMaterialBlacklistPO>
        implements RawMaterialBlacklistMpService {

}
