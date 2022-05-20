package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.AccountChuPO;
import com.diandong.mapper.AccountChuMapper;
import com.diandong.service.AccountChuMpService;
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
public class AccountChuMpServiceImpl extends CommonServiceImpl<AccountChuMapper, AccountChuPO>
        implements AccountChuMpService {

}
