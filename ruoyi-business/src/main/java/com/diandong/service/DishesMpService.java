package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.DishesDTO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.vo.DishesVO;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
public interface DishesMpService extends CommonService<DishesPO> {


    /**
     * 补充菜品信息
     *
     * @param dto
     */
    void getAllDishMsg(DishesDTO dto);

    /**
     * 新增菜品
     *
     * @param vo 菜品信息
     */
    void saveDishes(DishesVO vo);

    /**
     * 更新菜品
     *
     * @param vo
     */
    void updateDishes(DishesVO vo);

    /**
     * 获取数据 检测报告/原材料供应商信息/添加剂信息
     * @param id    菜品id
     * @return
     */
    BaseResult getMsg(String id);

    /**
     * 更新菜品 检测报告/原材料供应商信息/添加剂信息
     *
     * @param vo 菜品信息
     * @return
     */
    BaseResult saveOrUpdateMsg(DishesVO vo);
}
