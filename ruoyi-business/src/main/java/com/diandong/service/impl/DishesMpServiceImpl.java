package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.*;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.*;
import com.diandong.mapper.DishesMapper;
import com.diandong.mapstruct.*;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesMpServiceImpl extends CommonServiceImpl<DishesMapper, DishesPO>
        implements DishesMpService {

    @Resource
    private DishesNutritionMpService dishesNutritionMpService;
    @Resource
    private DishesRawMaterialMpService dishesRawMaterialMpService;
    @Resource
    private DishesSupplierMpService dishesSupplierMpService;
    @Resource
    private DishesAdditiveMpService dishesAdditiveMpService;

    @Override
    public void getAllDishMsg(DishesDTO dto) {

        Long dishId = dto.getId();

//        菜品营养信息
        List<DishesNutritionPO> dishesNutritionPOList = dishesNutritionMpService.lambdaQuery()
                .eq(DishesNutritionPO::getDishesId, dishId)
                .eq(DishesNutritionPO::getDelFlag, Constants.DEL_NO)
                .list();


        List<DishesRawMaterialPO> dishesRawMaterialPOList = dishesRawMaterialMpService.lambdaQuery()
                .eq(DishesRawMaterialPO::getDelFlag, Constants.DEL_NO)
                .eq(DishesRawMaterialPO::getDishesId, dishId)
                .list();

        List<DishesNutritionDTO> collect1 = dishesNutritionPOList.stream().map(dishesNutritionPO -> DishesNutritionMsMapper.INSTANCE.po2dto(dishesNutritionPO)).collect(Collectors.toList());
        List<DishesRawMaterialDTO> collect2 = dishesRawMaterialPOList.stream().map(dishesRawMaterialPO -> DishesRawMaterialMsMapper.INSTANCE.po2dto(dishesRawMaterialPO)).collect(Collectors.toList());

        dto.setDishesNutritionList(collect1);
        dto.setDishesRawMaterialList(collect2);


    }

    @Override
    public void saveDishes(DishesVO vo) {

        DishesPO po = DishesMsMapper.INSTANCE.vo2po(vo);
        po.setCode(RandomStringUtils.randomAlphabetic(8).toUpperCase(Locale.ROOT));
//        保存菜品基本信息
        save(po);
//        保存菜品原材料和菜品营养信息
        saveDishesInformation(vo, po);

    }

    @Override
    public void updateDishes(DishesVO vo) {
        DishesPO po = DishesMsMapper.INSTANCE.vo2po(vo);
        //        保存菜品基本信息
        saveOrUpdate(po);
        saveDishesInformation(vo, po);
    }

    @Override
    public BaseResult getMsg(String id) {

        DishesPO dishesPO = getById(id);
        DishesDTO dishesDTO = DishesMsMapper.INSTANCE.po2dto(dishesPO);
//        装配添加剂信息
        List<DishesAdditivePO> additivePOList = dishesAdditiveMpService.lambdaQuery().eq(DishesAdditivePO::getDishesId, id).list();
        List<DishesAdditiveDTO> additiveDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(additivePOList)) {
            additivePOList.forEach(dishesAdditivePO -> {
                additiveDTOList.add(DishesAdditiveMsMapper.INSTANCE.po2dto(dishesAdditivePO));
            });
        }
//        装配供应商信息
        List<DishesSupplierPO> supplierPOList = dishesSupplierMpService.lambdaQuery().eq(DishesSupplierPO::getDishesId, id).list();
        List<DishesSupplierDTO> supplierDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(supplierPOList)) {
            supplierPOList.forEach(dishesSupplierPO -> {
                supplierDTOList.add(DishesSupplierMsMapper.INSTANCE.po2dto(dishesSupplierPO));
            });
        }
        dishesDTO.setDishesAdditiveList(additiveDTOList);
        dishesDTO.setDishesSupplierList(supplierDTOList);
        return BaseResult.success(dishesDTO);
    }

    @Override
    public BaseResult saveOrUpdateMsg(DishesVO vo) {

        String testReport = vo.getTestReport();
        List<DishesSupplierVO> dishesSupplierList = vo.getDishesSupplierList();
        List<DishesAdditiveVO> dishesAdditiveList = vo.getDishesAdditiveList();

        if (StringUtils.isBlank(testReport) || CollectionUtils.isEmpty(dishesSupplierList) || CollectionUtils.isEmpty(dishesAdditiveList)) {
            return BaseResult.error("参数不全");
        }

        DishesPO dishesPO = DishesMsMapper.INSTANCE.vo2po(vo);
        saveOrUpdate(dishesPO);
//        删除原来的添加剂数据
        LambdaUpdateWrapper<DishesAdditivePO> additivePOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        additivePOLambdaUpdateWrapper.eq(DishesAdditivePO::getDishesId, vo.getId());
        dishesAdditiveMpService.remove(additivePOLambdaUpdateWrapper);
//        保存最新的添加剂数据
        List<DishesAdditivePO> additivePOList = new ArrayList<>();
        dishesAdditiveList.forEach(dishesAdditiveVO -> {
            dishesAdditiveVO.setDishesId(vo.getId());
            additivePOList.add(DishesAdditiveMsMapper.INSTANCE.vo2po(dishesAdditiveVO));
        });
        dishesAdditiveMpService.saveOrUpdateBatch(additivePOList);

//        删除原有的供应商数据
        LambdaUpdateWrapper<DishesSupplierPO> supplierPOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        supplierPOLambdaUpdateWrapper.eq(DishesSupplierPO::getDishesId, vo.getId());
        dishesSupplierMpService.remove(supplierPOLambdaUpdateWrapper);
//        保存最新的供应商数据
        List<DishesSupplierPO> supplierPOList = new ArrayList<>();
        dishesSupplierList.forEach(dishesSupplierVO -> {
            dishesSupplierVO.setDishesId(vo.getId());
            supplierPOList.add(DishesSupplierMsMapper.INSTANCE.vo2po(dishesSupplierVO));
        });
        dishesSupplierMpService.saveOrUpdateBatch(supplierPOList);


        return BaseResult.success();
    }

    private void saveDishesInformation(DishesVO vo, DishesPO po) {
        //        菜品原材料信息
        List<DishesRawMaterialVO> dishesRawMaterialList = vo.getDishesRawMaterialList();
        List<DishesRawMaterialPO> dishesRawMaterialPOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(dishesRawMaterialList)) {

            LambdaUpdateWrapper<DishesRawMaterialPO> removeWrapper = new LambdaUpdateWrapper<>();
            removeWrapper.eq(DishesRawMaterialPO::getDishesId, po.getId());
            dishesRawMaterialMpService.remove(removeWrapper);


            dishesRawMaterialList.forEach(dishesRawMaterialVO -> {
                DishesRawMaterialPO dishesRawMaterialPO = DishesRawMaterialMsMapper.INSTANCE.vo2po(dishesRawMaterialVO);
                dishesRawMaterialPO.setDishesId(po.getId());
                dishesRawMaterialPOList.add(dishesRawMaterialPO);
            });
            dishesRawMaterialMpService.saveBatch(dishesRawMaterialPOList);
        }


//        菜品营养信息
        List<DishesNutritionVO> dishesNutritionList = vo.getDishesNutritionList();
        List<DishesNutritionPO> dishesNutritionPOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dishesNutritionList)) {

            LambdaUpdateWrapper<DishesNutritionPO> removeWrapper = new LambdaUpdateWrapper<>();
            removeWrapper.eq(DishesNutritionPO::getDishesId, po.getId());
            dishesNutritionMpService.remove(removeWrapper);


            dishesNutritionList.forEach(dishesNutritionVO -> {
                DishesNutritionPO dishesNutritionPO = DishesNutritionMsMapper.INSTANCE.vo2po(dishesNutritionVO);
                dishesNutritionPO.setDishesId(po.getId());
                dishesNutritionPOList.add(dishesNutritionPO);
            });
            dishesNutritionMpService.saveBatch(dishesNutritionPOList);
        }


    }
}
