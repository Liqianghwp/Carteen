package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.ShopCartPO;
import com.diandong.mapper.ShopCartMapper;
import com.diandong.service.ShopCartMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopCartMpServiceImpl extends CommonServiceImpl<ShopCartMapper, ShopCartPO>
        implements ShopCartMpService {

}
