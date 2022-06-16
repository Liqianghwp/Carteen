package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.ShopCartVO;
import com.diandong.mapper.ShopCartMapper;
import com.diandong.mapstruct.ShopCartMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 购物车service实现类
 *
 * @author YuLiu
 * @date 2022-06-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopCartMpServiceImpl extends CommonServiceImpl<ShopCartMapper, ShopCartPO>
        implements ShopCartMpService {

    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private DishesMpService dishesMpService;
    @Resource
    private RecipeMpService recipeMpService;
    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private BizDictMpService bizDictMpService;

    @Override
    public BaseResult getList(ShopCartVO vo) {

        Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();
        if (Objects.isNull(diningCanteenId)) {
            return BaseResult.error("请选择就餐食堂");
        }
//        查询就餐食堂购物车列表
        List<ShopCartPO> list = lambdaQuery().eq(ShopCartPO::getCanteenId, diningCanteenId)
                .eq(ShopCartPO::getDelFlag, Constants.DEL_NO)
                .list();

        return BaseResult.success(list);
    }

    @Override
    public BaseResult save(ShopCartVO vo) {
        if (vo.getNumber() == 0) {
            return BaseResult.error("输入更改数量");
        }

        SysUser user = SecurityUtils.getLoginUser().getUser();
        CanteenPO canteen = canteenMpService.getById(user.getDiningCanteenId());

        List<ShopCartPO> list = lambdaQuery().eq(ShopCartPO::getCanteenId, canteen.getId())
                .eq(ShopCartPO::getDishesId, vo.getDishesId())
                .eq(ShopCartPO::getMealTimesId, vo.getMealTimesId())
                .list();

        RecipePO recipe = recipeMpService.lambdaQuery()
                .eq(RecipePO::getCanteenId, user.getDiningCanteenId())
                .eq(RecipePO::getRecipeDate, LocalDate.now())
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .one();

        RecipeDetailPO recipeDetail = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .eq(RecipeDetailPO::getRecipeId, recipe.getId())
                .eq(RecipeDetailPO::getMealTimesId, vo.getMealTimesId())
                .eq(RecipeDetailPO::getDishesId, vo.getDishesId())
                .last("limit 1").one();

//        未在购物车中找到改菜品
        if (CollectionUtils.isEmpty(list)) {
            if (vo.getNumber() < 0) {
                return BaseResult.error("菜品已经从购物车移除");
            }
            if (vo.getNumber() > recipeDetail.getNumber()) {
                return BaseResult.error("库存不足");
            }

//            保存购物信息
            saveShopCart(vo, canteen, user);
            return BaseResult.success();
        }

//        在购物车中找到该商品

        for (ShopCartPO shopCartPO : list) {

//            设置数量
            shopCartPO.setNumber(shopCartPO.getNumber() + vo.getNumber());
//            当数量小于0时清楚购物车中该菜品的信息
            Integer number = shopCartPO.getNumber();
            if (number <= 0) {
                removeById(shopCartPO.getId());
                return BaseResult.success();
            }
//            判断库存是否充足
            if (number > recipeDetail.getNumber()) {
                return BaseResult.error("库存不足");
            }
            updateById(shopCartPO);
        }
        return BaseResult.success();
    }


    private void saveShopCart(ShopCartVO vo, CanteenPO canteen, SysUser user) {
        //        设置购物车其他信息
        ShopCartPO shopCartPO = ShopCartMsMapper.INSTANCE.vo2po(vo);
        shopCartPO.setCanteenId(Objects.nonNull(canteen) ? canteen.getId() : user.getDiningCanteenId());
        shopCartPO.setCanteenName(Objects.nonNull(canteen) ? canteen.getCanteenName() : null);
//        补菜品名称

        if (StringUtils.isBlank(shopCartPO.getDishesName())) {
            DishesPO dishes = dishesMpService.getById(shopCartPO.getDishesId());
            shopCartPO.setDishesName(Objects.nonNull(dishes) ? dishes.getDishesName() : null);
        }

        if(StringUtils.isBlank(shopCartPO.getMealTimesName())){
            BizDictPO mealTimes = bizDictMpService.getById(vo.getMealTimesId());
            shopCartPO.setMealTimesName(mealTimes.getDictLabel());
        }
        save(shopCartPO);
    }


}
