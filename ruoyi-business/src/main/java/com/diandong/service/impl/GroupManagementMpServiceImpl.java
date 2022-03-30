package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.mapper.GroupManagementMapper;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupManagementMpServiceImpl extends CommonServiceImpl<GroupManagementMapper, GroupManagementPO>
        implements GroupManagementMpService {

}
