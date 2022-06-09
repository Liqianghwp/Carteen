package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.UserAmountPO;
import com.diandong.mapper.UserAmountMapper;
import com.diandong.service.UserAmountMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 用户金额service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserAmountMpServiceImpl extends CommonServiceImpl<UserAmountMapper, UserAmountPO>
        implements UserAmountMpService {

}
