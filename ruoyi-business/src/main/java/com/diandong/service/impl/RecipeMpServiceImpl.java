    package com.diandong.service.impl;

    import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
    import com.diandong.configuration.CommonServiceImpl;
    import com.diandong.domain.dto.RawMaterialDTO;
    import com.diandong.domain.po.DishesRawMaterialPO;
    import com.diandong.domain.po.RawMaterialPO;
    import com.diandong.domain.po.RecipeDetailPO;
    import com.diandong.domain.po.RecipePO;
    import com.diandong.domain.vo.RecipeDetailVO;
    import com.diandong.domain.vo.RecipeVO;
    import com.diandong.mapper.RecipeMapper;
    import com.diandong.mapstruct.RawMaterialMsMapper;
    import com.diandong.mapstruct.RecipeMsMapper;
    import com.diandong.service.DishesRawMaterialMpService;
    import com.diandong.service.RawMaterialMpService;
    import com.diandong.service.RecipeDetailMpService;
    import com.diandong.service.RecipeMpService;
    import com.ruoyi.common.core.domain.BaseResult;
    import com.ruoyi.common.core.domain.model.LoginUser;
    import org.springframework.beans.BeanUtils;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import javax.annotation.Resource;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;
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

        @Override
        public BaseResult recipePost(RecipeVO vo, LoginUser loginUser) throws Exception {
            Boolean result = false;
            if (Objects.isNull(vo.getId())) {
    //            触发新增
                result = addRecipe(vo, loginUser);
            } else {
    //            触发更新
                result = updateRecipe(vo, loginUser);
            }
    //        判断添加成功与否
            if (result) {
                return BaseResult.successMsg("食谱添加成功");
            } else {
                return BaseResult.error("食谱添加失败");
            }
        }

        @Override
        public BaseResult copyRecipe(Long id, LocalDate recipeDate, LoginUser loginUser) throws Exception {

    //        要复制的菜谱信息
            RecipePO sourceRecipe = getById(id);
    //        目标菜谱
            RecipePO targetRecipe = new RecipePO();

            targetRecipe.setRecipeName(recipeDate.format(DateTimeFormatter.ISO_DATE));
            targetRecipe.setRecipeDate(recipeDate);
            targetRecipe.setAddWayId(sourceRecipe.getAddWayId());
            targetRecipe.setAddWayName(sourceRecipe.getAddWayName());
            targetRecipe.setVersion(sourceRecipe.getVersion());
            targetRecipe.setCreateBy(loginUser.getUserId());
            targetRecipe.setCreateName(loginUser.getUsername());

            boolean result = save(targetRecipe);

            if (result) {

                List<RecipeDetailPO> recipeDetailList = recipeDetailMpService.lambdaQuery()
                        .eq(RecipeDetailPO::getRecipeId, id)
                        .eq(RecipeDetailPO::getStatus, 0)
                        .list();

                recipeDetailList.forEach(recipeDetailPO -> {

                    recipeDetailPO.setId(null);
                    recipeDetailPO.setRecipeId(targetRecipe.getId());
                    recipeDetailPO.setRecipeName(targetRecipe.getRecipeName());
                    recipeDetailPO.setCreateBy(loginUser.getUserId());
                    recipeDetailPO.setCreateName(loginUser.getUsername());

                    recipeDetailPO.setUpdateBy(null);
                    recipeDetailPO.setUpdateName(null);
                    recipeDetailPO.setUpdateTime(null);
                });

                result = recipeDetailMpService.saveBatch(recipeDetailList);
                if (!result) {
                    throw new Exception("复制食谱失败");
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
        public BaseResult rawMaterialsList(List<RecipeDetailVO> voList) {

            List<RawMaterialDTO> rawMaterialDTOList = new ArrayList<>();


            for (RecipeDetailVO recipeDetailVO : voList) {
    //            查询菜品原材料信息
                List<DishesRawMaterialPO> dishesRawMaterialList = dishesRawMaterialMpService.lambdaQuery()
                        .eq(DishesRawMaterialPO::getDishesId, recipeDetailVO.getDishesId())
                        .eq(DishesRawMaterialPO::getDataState, 0)
                        .list();

                if (CollectionUtils.isNotEmpty(dishesRawMaterialList)) {

                    for (DishesRawMaterialPO dishesRawMaterialPO : dishesRawMaterialList) {
    //                    查询原材料信息

                        RawMaterialDTO rawMaterialDTO = RawMaterialMsMapper.INSTANCE.po2dto(rawMaterialMpService.getById(dishesRawMaterialPO.getRawMaterialId()));
                        double v = recipeDetailVO.getNumber() * dishesRawMaterialPO.getNumber();


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
            return BaseResult.success(rawMaterialDTOList);
        }


    //    @Override
    //    public BaseResult saveList(List<RecipeVO> voList, LoginUser loginUser) throws Exception {
    //
    //        List<RecipePO> poList = new ArrayList<>();
    //
    //        for (RecipeVO recipeVO : voList) {
    //            RecipePO po = RecipeMsMapper.INSTANCE.vo2po(recipeVO);
    //
    //            po.setCreateBy(loginUser.getUserId());
    //            po.setCreateName(loginUser.getUsername());
    //            poList.add(po);
    //        }
    //        boolean result = saveBatch(poList);
    //
    //        if (result) {
    //            return BaseResult.successMsg("食谱添加成功");
    //        } else {
    //            return BaseResult.error("食谱添加失败");
    //        }
    //    }

        /**
         * 新增菜谱
         *
         * @return
         */
        private Boolean addRecipe(RecipeVO vo, LoginUser loginUser) {
            RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
    //        设置创建人信息
            po.setCreateBy(loginUser.getUserId());
            po.setCreateName(loginUser.getUsername());
            boolean result = save(po);
            if (!result) {
                return result;
            }

            List<RecipeDetailVO> recipeDetailList = vo.getRecipeDetailList();

            List<RecipeDetailPO> detailPOList = new ArrayList<>();
            recipeDetailList.forEach(recipeDetailVO -> {
                RecipeDetailPO recipeDetailPO = new RecipeDetailPO();
                BeanUtils.copyProperties(recipeDetailVO, recipeDetailPO);
                recipeDetailPO.setRecipeId(po.getId());
                recipeDetailPO.setRecipeName(po.getRecipeName());
                recipeDetailPO.setCreateBy(loginUser.getUserId());
                recipeDetailPO.setCreateName(loginUser.getUsername());
                detailPOList.add(recipeDetailPO);
            });

            result = recipeDetailMpService.saveBatch(detailPOList);


            return result;
        }

        /**
         * 更新菜谱
         *
         * @return
         */
        private Boolean updateRecipe(RecipeVO vo, LoginUser loginUser) throws Exception {

            RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
    //        设置创建人信息
            po.setUpdateBy(loginUser.getUserId());
            po.setUpdateName(loginUser.getUsername());
            boolean result = updateById(po);
            if (!result) {
                return result;
            }
    //        要增加的食谱菜品ID集合
            List<RecipeDetailVO> recipeDetailList = vo.getRecipeDetailList();
            if (CollectionUtils.isNotEmpty(recipeDetailList)) {
                List<RecipeDetailPO> detailPOList = new ArrayList<>();
                recipeDetailList.forEach(recipeDetailVO -> {
                    RecipeDetailPO recipeDetailPO = new RecipeDetailPO();
                    BeanUtils.copyProperties(recipeDetailVO, recipeDetailPO);
                    recipeDetailPO.setRecipeId(po.getId());
                    recipeDetailPO.setRecipeName(po.getRecipeName());
                    recipeDetailPO.setCreateBy(loginUser.getUserId());
                    recipeDetailPO.setCreateName(loginUser.getUsername());
                    detailPOList.add(recipeDetailPO);
                });

                result = recipeDetailMpService.saveBatch(detailPOList);
                if (!result) {
                    throw new Exception("更新食谱失败");
                }
            }
    //        要删除的食谱菜品ID集合
            List<Long> detailIdList = vo.getDelRecipeDetailIdList();
            if (CollectionUtils.isNotEmpty(detailIdList)) {
                result = recipeDetailMpService.removeByIds(detailIdList);
            }
            if (!result) {
                throw new Exception("更新食谱失败");
            }
            return result;
        }


    }
