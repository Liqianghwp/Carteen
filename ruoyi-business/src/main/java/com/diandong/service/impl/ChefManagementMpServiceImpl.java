package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.ChefManagementPO;
import com.diandong.mapper.ChefManagementMapper;
import com.diandong.service.ChefManagementMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 厨师管理service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChefManagementMpServiceImpl extends CommonServiceImpl<ChefManagementMapper, ChefManagementPO>
        implements ChefManagementMpService {

}
