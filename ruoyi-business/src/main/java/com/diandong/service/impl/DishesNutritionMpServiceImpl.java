package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.vo.DishesNutritionVO;
import com.diandong.mapper.DishesNutritionMapper;
import com.diandong.mapstruct.DishesNutritionMsMapper;
import com.diandong.service.DishesNutritionMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesNutritionMpServiceImpl extends CommonServiceImpl<DishesNutritionMapper, DishesNutritionPO>
        implements DishesNutritionMpService {

    @Override
    public Boolean saveList(List<DishesNutritionPO> dnList) {
        Boolean result = false;

        for (DishesNutritionPO dishesNutritionPO : dnList) {

            result = save(dishesNutritionPO);

            if (!result) {
                throw new ServiceException("保存菜品营养信息失败");
            }
        }
        return result;
    }


    @Override
    public BaseResult saveDishesNutritionList(List<DishesNutritionVO> dnList, LoginUser loginUser) {

        Boolean result = false;
        for (DishesNutritionVO dishesNutritionVO : dnList) {
            DishesNutritionPO po = DishesNutritionMsMapper.INSTANCE.vo2po(dishesNutritionVO);

//            设置创建人信息
            po.setCreateBy(loginUser.getUserId());
            po.setCreateName(loginUser.getUsername());

            result = save(po);
            if (!result) {
                throw new ServiceException("保存菜品营养信息失败");
            }
        }
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败!");
        }
    }
}
