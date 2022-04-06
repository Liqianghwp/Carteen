package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.ShopCartDetailPO;
import com.diandong.mapper.ShopCartDetailMapper;
import com.diandong.service.ShopCartDetailMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-04-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopCartDetailMpServiceImpl extends CommonServiceImpl<ShopCartDetailMapper, ShopCartDetailPO>
        implements ShopCartDetailMpService {

}
