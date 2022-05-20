package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.AccountPO;
import com.diandong.mapper.AccountMapper;
import com.diandong.service.AccountMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountMpServiceImpl extends CommonServiceImpl<AccountMapper, AccountPO>
        implements AccountMpService {

}
