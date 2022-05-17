package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.vo.DishesRawMaterialVO;
import com.diandong.mapper.DishesRawMaterialMapper;
import com.diandong.mapstruct.DishesRawMaterialMsMapper;
import com.diandong.service.DishesNutritionMpService;
import com.diandong.service.DishesRawMaterialMpService;
import com.diandong.service.RawMaterialNutritionMpService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesRawMaterialMpServiceImpl extends CommonServiceImpl<DishesRawMaterialMapper, DishesRawMaterialPO>
        implements DishesRawMaterialMpService {

    //    原材料营养信息service
    @Resource
    private RawMaterialNutritionMpService rawMaterialNutritionMpService;
    //    菜品营养信息Service
    @Resource
    private DishesNutritionMpService dishesNutritionMpService;


    @Override
    public Boolean saveList(List<DishesRawMaterialVO> voList, LoginUser loginUser) {
//        保存原材料信息
        Boolean result = false;
//        要保存的菜品营养信息
        List<DishesNutritionPO> dnList = new ArrayList<>();

//        菜品原材料查询结果
        for (DishesRawMaterialVO dishesRawMaterialVO : voList) {
            DishesRawMaterialPO po = DishesRawMaterialMsMapper.INSTANCE.vo2po(dishesRawMaterialVO);
//            保存菜品原材料信息
            result = save(po);
            if (!result) {
                throw new ServiceException("保存菜品原材料信息失败");
            }

//            查询原材料营养信息
//            查询原材料未删除的营养信息
//            原材料未删除的营养信息结果

            List<RawMaterialNutritionPO> list = rawMaterialNutritionMpService.lambdaQuery()
                    .eq(RawMaterialNutritionPO::getRawMaterialId, dishesRawMaterialVO.getRawMaterialId())
                    .eq(RawMaterialNutritionPO::getDelFlag, false)
                    .list();

            resetDishesNutritionPO(dnList, dishesRawMaterialVO, list, loginUser);
        }

//        保存原材料营养信息
        if (CollectionUtils.isNotEmpty(dnList)) {

            result = dishesNutritionMpService.saveList(dnList);
        }


//        整体流程走完之后才会返回成功

        return result;
    }

    /**
     * 获取菜品营养信息
     *
     * @param dnList              菜品营养信息集合
     * @param dishesRawMaterialVO 菜品原材料信息
     * @param list                原材料营养信息集合
     */
    private void resetDishesNutritionPO(List<DishesNutritionPO> dnList, DishesRawMaterialVO dishesRawMaterialVO, List<RawMaterialNutritionPO> list, LoginUser loginUser) {

        if (CollectionUtils.isNotEmpty(list)) {

            for (RawMaterialNutritionPO rawMaterialNutritionPO : list) {
//                    某个菜品某样原材料的某个营养信息的含量

                Double needNumber = dishesRawMaterialVO.getNumber() * rawMaterialNutritionPO.getNumber();
                List<DishesNutritionPO> collect = dnList.stream().filter(dishesNutritionPO -> Objects.equals(dishesNutritionPO.getNutritionId(), rawMaterialNutritionPO.getNutritionParamsId())).collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(collect)) {
//                        当前集合内有这个原材料信息
                    DishesNutritionPO dishesNutritionPO = collect.get(0);

                    dnList.stream().forEach(dishesNutritionPO1 -> {
                        if (Objects.equals(dishesNutritionPO1.getNutritionId(), rawMaterialNutritionPO.getNutritionParamsId())) {
//                                重新设置菜品营养信息值
                            dishesNutritionPO1.setNumber(dishesNutritionPO1.getNumber() + needNumber);
                        }
                    });
                } else {

                    DishesNutritionPO dnPo = new DishesNutritionPO();

                    dnPo.setDishesId(dishesRawMaterialVO.getDishesId());
                    dnPo.setDishesName(dishesRawMaterialVO.getDishesName());
                    dnPo.setNutritionId(rawMaterialNutritionPO.getNutritionParamsId());
//                        这个地方没有设置营养元素的名称
//                        dnPo.setNutritionName(rawMaterialNutritionPO.get)
                    dnPo.setNumber(needNumber);

                    dnPo.setCreateBy(loginUser.getUserId());
                    dnPo.setCreateTime(LocalDateTime.now());

                    dnList.add(dnPo);
                }
            }
        }

    }
}
