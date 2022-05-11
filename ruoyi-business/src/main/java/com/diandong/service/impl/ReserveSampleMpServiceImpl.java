package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.ReserveSamplePO;
import com.diandong.mapper.ReserveSampleMapper;
import com.diandong.service.ReserveSampleMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 预留样品service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReserveSampleMpServiceImpl extends CommonServiceImpl<ReserveSampleMapper, ReserveSamplePO>
        implements ReserveSampleMpService {

}
