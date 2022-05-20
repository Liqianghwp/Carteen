package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.AccountRuPO;
import com.diandong.mapper.AccountRuMapper;
import com.diandong.service.AccountRuMpService;
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
public class AccountRuMpServiceImpl extends CommonServiceImpl<AccountRuMapper, AccountRuPO>
        implements AccountRuMpService {

}
