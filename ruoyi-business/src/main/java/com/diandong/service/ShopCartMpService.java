package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.ShopCartPO;
import com.diandong.domain.vo.ShopCartVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-04-06
 */
public interface ShopCartMpService extends CommonService<ShopCartPO> {


    /**
     * 添加购物车
     *
     * @param shopCartVO 购物车信息
     * @param shopCartVO
     * @return BaseResult
     */
    BaseResult saveShopCart(ShopCartVO shopCartVO, LoginUser loginUser) throws Exception;

}
