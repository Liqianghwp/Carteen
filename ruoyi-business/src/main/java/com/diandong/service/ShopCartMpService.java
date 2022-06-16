package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.ShopCartPO;
import com.diandong.domain.vo.ShopCartVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * 购物车Service接口类
 *
 * @author YuLiu
 * @date 2022-06-14
 */
public interface ShopCartMpService extends CommonService<ShopCartPO> {


    /**
     * 购物车列表
     * @param vo
     * @return
     */
    BaseResult getList(ShopCartVO vo);

    /**
     * 添加购物车
     *
     * @param vo 要添加的购物车信息
     * @return
     */
    BaseResult save(ShopCartVO vo);

}
