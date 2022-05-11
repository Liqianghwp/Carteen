package com.diandong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.ShopCartDetailPO;
import com.diandong.domain.po.ShopCartPO;
import com.diandong.domain.vo.ShopCartDetailVO;
import com.diandong.domain.vo.ShopCartVO;
import com.diandong.enums.DataStateEnum;
import com.diandong.mapper.ShopCartMapper;
import com.diandong.mapstruct.ShopCartDetailMsMapper;
import com.diandong.mapstruct.ShopCartMsMapper;
import com.diandong.service.ShopCartDetailMpService;
import com.diandong.service.ShopCartMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-04-06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopCartMpServiceImpl extends CommonServiceImpl<ShopCartMapper, ShopCartPO>
        implements ShopCartMpService {

    /**
     * 购物车详情Service
     */
    @Resource
    private ShopCartDetailMpService shopCartDetailMpService;

    @Override
    public BaseResult saveShopCart(ShopCartVO shopCartVO, LoginUser loginUser) throws Exception {

//        保存购物车信息
        Boolean result = false;
//        查询购物车信息

        LambdaQueryWrapper<ShopCartPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(ShopCartPO::getCanteenId, shopCartVO.getCanteenId())
                .eq(ShopCartPO::getCreateBy, loginUser.getUserId())
                .eq(ShopCartPO::getDelFlag, DataStateEnum.DELETE_NO.value());
        ShopCartPO shopCartPO = getOne(lambdaQueryWrapper);

        if (Objects.isNull(shopCartPO)) {
            shopCartPO = ShopCartMsMapper.INSTANCE.vo2po(shopCartVO);
            shopCartPO.setCreateBy(loginUser.getUserId());
            result = save(shopCartPO);
        } else {
            result = true;
        }

//        保存购物车详情信息

        List<ShopCartDetailVO> shopCartDetailVOList = shopCartVO.getShopCartDetailVOList();
//        如果购物车详情不为空则进行详情处理
        if (result && CollectionUtils.isNotEmpty(shopCartDetailVOList)) {

            List<ShopCartDetailPO> detailPOList = new ArrayList<>();
            ShopCartPO finalShopCartPO = shopCartPO;
            shopCartDetailVOList.forEach(shopCartDetailVO -> {

                shopCartDetailVO.setShopCartId(finalShopCartPO.getId());
                ShopCartDetailPO shopCartDetailPO = ShopCartDetailMsMapper.INSTANCE.vo2po(shopCartDetailVO);
                shopCartDetailPO.setCreateBy(loginUser.getUserId());

                log.info(JSONObject.toJSONString(shopCartDetailPO));
                detailPOList.add(shopCartDetailPO);
            });


//            for (ShopCartDetailVO shopCartDetailVO : shopCartDetailVOList) {
//
//                shopCartDetailVO.setShopCartId(shopCartPO.getId());
//                ShopCartDetailPO shopCartDetailPO = ShopCartDetailMsMapper.INSTANCE.vo2po(shopCartDetailVO);
//                shopCartDetailPO.setCreateBy(loginUser.getUserId());
//                shopCartDetailPO.setCreateName(loginUser.getUsername());
//
//                log.info(JSONObject.toJSONString(shopCartDetailPO));
//                detailPOList.add(shopCartDetailPO);
//
//            }


            result = shopCartDetailMpService.saveBatch(detailPOList);
        }

//        判断结果
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
//            当添加失败时，对数据进行回滚
            throw new Exception("添加失败");
//            return BaseResult.error("添加失败！");
        }
    }
}
