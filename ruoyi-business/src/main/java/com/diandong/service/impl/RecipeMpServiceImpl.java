package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.CanteenPurchaseState;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.RecipeDetailVO;
import com.diandong.domain.vo.RecipeVO;
import com.diandong.mapper.RecipeMapper;
import com.diandong.mapstruct.RawMaterialMsMapper;
import com.diandong.mapstruct.RecipeDetailMsMapper;
import com.diandong.mapstruct.RecipeMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.constant.MealSettingConstants;
import com.ruoyi.system.constant.SysConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecipeMpServiceImpl extends CommonServiceImpl<RecipeMapper, RecipePO>
        implements RecipeMpService {

    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private DishesRawMaterialMpService dishesRawMaterialMpService;
    @Resource
    private RawMaterialMpService rawMaterialMpService;
    //    @Resource
//    private ISysDictDataService dataService;
    @Resource
    private BizDictMpService bizDictMpService;
    @Resource
    private DishesMpService dishesMpService;
    @Resource
    private ChefManagementMpService chefManagementMpService;
    @Resource
    private CanteenPurchaseMpService canteenPurchaseMpService;
    @Resource
    private CanteenMpService canteenMpService;


    @Override
    public BaseResult recipePost(RecipeVO vo) {

        if (Objects.nonNull(vo.getId())) {
//            当食谱ID不为空时删除对应的详情数据
            List<RecipeDetailPO> list = recipeDetailMpService.lambdaQuery().eq(RecipeDetailPO::getRecipeId, vo.getId()).list();
            if (CollectionUtils.isNotEmpty(list)) {
                List<Long> collect = list.stream().map(RecipeDetailPO::getId).collect(Collectors.toList());
                recipeDetailMpService.removeByIds(collect);
            }
        } else {
            Integer count = lambdaQuery().eq(RecipePO::getRecipeDate, vo.getRecipeDate())
                    .eq(RecipePO::getCanteenId, SecurityUtils.getCanteenId())
                    .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                    .count();
            if (count > 0) {
                return BaseResult.error("该日期已有食谱信息");
            }
        }

        RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
        //        设置创建人信息
        po.setCanteenId(SecurityUtils.getCanteenId());
        boolean result = saveOrUpdate(po);
        if (!result) {
            return BaseResult.error("保存食谱信息失败");
        }

        List<RecipeDetailVO> recipeDetailList = vo.getRecipeDetailList();

        List<RecipeDetailPO> detailPOList = new ArrayList<>();
//        for (RecipeDetailVO recipeDetailVO : recipeDetailList) {


        recipeDetailList.forEach(recipeDetailVO -> {
//            RecipeDetailPO recipeDetailPO = new RecipeDetailPO();
//            BeanUtils.copyProperties(recipeDetailVO, recipeDetailPO);
            RecipeDetailPO recipeDetailPO = RecipeDetailMsMapper.INSTANCE.vo2po(recipeDetailVO);

            recipeDetailPO.setRecipeId(po.getId());
            recipeDetailPO.setRecipeName(po.getRecipeName());
//            餐次名称为空时
            if (StringUtils.isBlank(recipeDetailPO.getMealTimesName())) {
                BizDictPO bizDictPo = bizDictMpService.getById(recipeDetailPO.getMealTimesId());
                recipeDetailPO.setMealTimesName(Objects.nonNull(bizDictPo) ? bizDictPo.getDictLabel() : null);
            }
//            菜品名称为空时
            DishesPO dishesPO = dishesMpService.getById(recipeDetailPO.getDishesId());
            if (StringUtils.isBlank(recipeDetailPO.getDishesName())) {
                recipeDetailPO.setDishesName(Objects.nonNull(dishesPO) ? dishesPO.getDishesName() : null);
            }
            recipeDetailPO.setDishesTypeId(Objects.nonNull(dishesPO) ? dishesPO.getDishesTypeId() : null);
            recipeDetailPO.setDishesTypeName(Objects.nonNull(dishesPO) ? dishesPO.getDishesTypeName() : null);
//            厨师名称为空时
            if (StringUtils.isBlank(recipeDetailPO.getChefName())) {
                ChefManagementPO chef = chefManagementMpService.getById(recipeDetailPO.getChefId());
                recipeDetailPO.setChefName(Objects.nonNull(chef) ? chef.getChefName() : null);
            }


            detailPOList.add(recipeDetailPO);
        });
//        }
        result = recipeDetailMpService.saveBatch(detailPOList);
        //        判断添加成功与否
        if (result) {
            return BaseResult.successMsg("食谱添加成功");
        } else {
            return BaseResult.error("食谱添加失败");
        }
    }

    @Override
    public BaseResult copyRecipe(Long id, LocalDate recipeDate) {

        //        要复制的菜谱信息
        RecipePO sourceRecipe = getById(id);
        //        目标菜谱
        RecipePO targetRecipe = new RecipePO();

        targetRecipe.setCanteenId(sourceRecipe.getCanteenId());
        targetRecipe.setRecipeName(recipeDate.format(DateTimeFormatter.ISO_DATE));
        targetRecipe.setRecipeDate(recipeDate);
        targetRecipe.setAddWayId(sourceRecipe.getAddWayId());
        targetRecipe.setAddWayName(sourceRecipe.getAddWayName());

        boolean result = save(targetRecipe);

        if (result) {

            List<RecipeDetailPO> recipeDetailList = recipeDetailMpService.lambdaQuery()
                    .eq(RecipeDetailPO::getRecipeId, id)
                    .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                    .list();

            recipeDetailList.forEach(recipeDetailPO -> {

                recipeDetailPO.setId(null);
                recipeDetailPO.setRecipeId(targetRecipe.getId());
                recipeDetailPO.setRecipeName(targetRecipe.getRecipeName());

                recipeDetailPO.setUpdateBy(null);
                recipeDetailPO.setUpdateTime(null);
            });

            result = recipeDetailMpService.saveBatch(recipeDetailList);
            if (!result) {
                throw new ServiceException("复制食谱失败");
            }
        }
        //        判断操作状态
        if (result) {
            return BaseResult.successMsg("复制食谱成功");
        } else {
            return BaseResult.error("复制食谱失败");
        }

    }

    @Override
    public BaseResult rawMaterialsList(String recipeId) {

        List<RecipeDetailPO> list = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getRecipeId, recipeId)
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .list();


        List<RawMaterialDTO> rawMaterialDTOList = new ArrayList<>();

        for (RecipeDetailPO recipeDetailPO : list) {
            getRawMaterialList(rawMaterialDTOList, recipeDetailPO);
        }
        return BaseResult.success(rawMaterialDTOList);
    }

    @Override
    public BaseResult recipeSourcing(String recipeId) {

        List<RecipeDetailPO> list = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getRecipeId, recipeId)
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .list();

        List<RawMaterialDTO> rawMaterialDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (RecipeDetailPO recipeDetailPO : list) {
                getRawMaterialList(rawMaterialDTOList, recipeDetailPO);
            }
        }
        return BaseResult.success(rawMaterialDTOList);
    }

    @Override
    public BaseResult createCanteenPurchase(String recipeId) {

        RecipePO recipe = getById(recipeId);
//        Integer count = canteenPurchaseMpService.lambdaQuery()
//                .ge(CanteenPurchasePO::getRecipeStartDate, recipe.getRecipeDate())
//                .le(CanteenPurchasePO::getRecipeEndDate, recipe.getRecipeDate())
//                .eq(CanteenPurchasePO::getDelFlag, Constants.DEL_NO)
//                .count();
        String recipeDate = recipe.getRecipeDate().format(DateTimeFormatter.ISO_DATE);

        Integer count = canteenPurchaseMpService.lambdaQuery()
                .like(CanteenPurchasePO::getValidDate, recipeDate)
                .eq(CanteenPurchasePO::getDelFlag, Constants.DEL_NO)
                .count();

        if (count != 0) {
            return BaseResult.error("食谱已被记录");
        }

        CanteenPurchasePO canteenPurchase = new CanteenPurchasePO();

        Long canteenId = SecurityUtils.getCanteenId();
        CanteenPO canteen = canteenMpService.getById(canteenId);
        canteenPurchase.setCanteenId(canteenId);
        canteenPurchase.setCanteenName(canteen.getCanteenName());
        canteenPurchase.setRecipeStartDate(recipe.getRecipeDate());
        canteenPurchase.setRecipeEndDate(recipe.getRecipeDate());
        canteenPurchase.setValidDate(recipeDate);
        canteenPurchase.setDays(1);
        canteenPurchase.setState(CanteenPurchaseState.UN_SUBMIT);
        canteenPurchaseMpService.save(canteenPurchase);

        return BaseResult.success();
    }

    private void getRawMaterialList(List<RawMaterialDTO> rawMaterialDTOList, RecipeDetailPO recipeDetail) {
        //            查询菜品原材料信息
        List<DishesRawMaterialPO> dishesRawMaterialList = dishesRawMaterialMpService.lambdaQuery()
                .eq(DishesRawMaterialPO::getDishesId, recipeDetail.getDishesId())
                .eq(DishesRawMaterialPO::getDelFlag, Constants.DEL_NO)
                .list();

        if (CollectionUtils.isNotEmpty(dishesRawMaterialList)) {

            for (DishesRawMaterialPO dishesRawMaterialPO : dishesRawMaterialList) {
                //                    查询原材料信息

                RawMaterialDTO rawMaterialDTO = RawMaterialMsMapper.INSTANCE.po2dto(rawMaterialMpService.getById(dishesRawMaterialPO.getRawMaterialId()));
                double v = recipeDetail.getNumber() * dishesRawMaterialPO.getNumber();

                List<RawMaterialDTO> collect = rawMaterialDTOList.stream().filter(rawMaterial -> rawMaterial.getId() == rawMaterialDTO.getId()).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    RawMaterialDTO rawMaterialDTO1 = collect.get(0);
                    rawMaterialDTO1.setNumber(rawMaterialDTO1.getNumber() + v);
                } else {
                    rawMaterialDTO.setNumber(v);
                    rawMaterialDTOList.add(rawMaterialDTO);
                }
            }
        }
    }


    @Override
    public void resetRecipeList(List<RecipePO> records) {

        List<Long> collect = records.stream().map(RecipePO::getId).collect(Collectors.toList());

        List<BizDictPO> bizDictList = bizDictMpService.lambdaQuery().eq(BizDictPO::getDictType, SysConstants.MEAL_SETTING).list();

        Long breakfastCode = 0L;
        Long lunchCode = 0L;
        Long dinnerCode = 0L;

        for (BizDictPO dictData : bizDictList) {
            switch (dictData.getDictValue()) {
                case MealSettingConstants.BREAKFAST:
                    breakfastCode = dictData.getId();
                    break;
                case MealSettingConstants.LUNCH:
                    lunchCode = dictData.getId();
                    break;
                case MealSettingConstants.DINNER:
                    dinnerCode = dictData.getId();
                    break;
            }
        }

//            餐次字典id集合
        List<Long> mealIds = Arrays.asList(breakfastCode, lunchCode, dinnerCode);

//            食谱详情
        List<RecipeDetailPO> list = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .in(RecipeDetailPO::getRecipeId, collect)
                .in(RecipeDetailPO::getMealTimesId, mealIds)
                .list();


        for (RecipePO record : records) {
//                当前食谱的
            List<RecipeDetailPO> collect1 = list.stream().filter(recipeDetailPO -> recipeDetailPO.getRecipeId() == record.getId()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect1)) {

                Map<Long, List<RecipeDetailPO>> mealsMap = collect1.stream().collect(Collectors.groupingBy(RecipeDetailPO::getMealTimesId));

                for (Map.Entry<Long, List<RecipeDetailPO>> entry : mealsMap.entrySet()) {
                    Long key = entry.getKey();
                    String dishesName = entry.getValue().stream().map(RecipeDetailPO::getDishesName).collect(Collectors.joining(","));
                    if (key == breakfastCode) {
                        record.setBreakfastMsg(dishesName);
                    } else if (key == lunchCode) {
                        record.setLunchMsg(dishesName);
                    } else if (key == dinnerCode) {
                        record.setDinnerMsg(dishesName);
                    }
                }
            }
        }
    }


    /**
     * 新增菜谱
     *
     * @return
     */
//    private Boolean addRecipe(RecipeVO vo) {
//        RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
//        //        设置创建人信息
//        po.setCanteenId(SecurityUtils.getCanteenId());
//        boolean result = save(po);
//        if (!result) {
//            return result;
//        }
//
//        List<RecipeDetailVO> recipeDetailList = vo.getRecipeDetailList();
//
//        List<RecipeDetailPO> detailPOList = new ArrayList<>();
//        recipeDetailList.forEach(recipeDetailVO -> {
//            RecipeDetailPO recipeDetailPO = new RecipeDetailPO();
//            BeanUtils.copyProperties(recipeDetailVO, recipeDetailPO);
//            recipeDetailPO.setRecipeId(po.getId());
//            recipeDetailPO.setRecipeName(po.getRecipeName());
//            detailPOList.add(recipeDetailPO);
//        });
//
//        result = recipeDetailMpService.saveBatch(detailPOList);
//
//        return result;
//    }

    /**
     * 更新菜谱
     *
     * @return
     */
//    private Boolean updateRecipe(RecipeVO vo) {
//
//        RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
//        //        设置创建人信息
//        boolean result = updateById(po);
//        if (!result) {
//            return result;
//        }
//        //        要增加的食谱菜品ID集合
//        List<RecipeDetailVO> recipeDetailList = vo.getRecipeDetailList();
//        if (CollectionUtils.isNotEmpty(recipeDetailList)) {
//            List<RecipeDetailPO> detailPOList = new ArrayList<>();
//            recipeDetailList.forEach(recipeDetailVO -> {
//                RecipeDetailPO recipeDetailPO = new RecipeDetailPO();
//                BeanUtils.copyProperties(recipeDetailVO, recipeDetailPO);
//                recipeDetailPO.setRecipeId(po.getId());
//                recipeDetailPO.setRecipeName(po.getRecipeName());
//                detailPOList.add(recipeDetailPO);
//            });
//
//            result = recipeDetailMpService.saveBatch(detailPOList);
//            if (!result) {
//                throw new ServiceException("更新食谱失败");
//            }
//        }
//        //        要删除的食谱菜品ID集合
//        List<Long> detailIdList = vo.getDelRecipeDetailIdList();
//        if (CollectionUtils.isNotEmpty(detailIdList)) {
//            result = recipeDetailMpService.removeByIds(detailIdList);
//        }
//        if (!result) {
//            throw new ServiceException("更新食谱失败");
//        }
//        return result;
//    }


}
