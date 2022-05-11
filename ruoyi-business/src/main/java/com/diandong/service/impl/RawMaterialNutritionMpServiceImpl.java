package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.vo.RawMaterialNutritionVO;
import com.diandong.mapper.RawMaterialNutritionMapper;
import com.diandong.mapstruct.RawMaterialNutritionMsMapper;
import com.diandong.service.RawMaterialNutritionMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RawMaterialNutritionMpServiceImpl extends CommonServiceImpl<RawMaterialNutritionMapper, RawMaterialNutritionPO>
        implements RawMaterialNutritionMpService {

    @Override
    public BaseResult addList(List<RawMaterialNutritionVO> voList, LoginUser loginUser) throws Exception {

        //        营养配料信息

        if (CollectionUtils.isNotEmpty(voList)) {
//            原材料id编码
            for (RawMaterialNutritionVO rawMaterialNutritionVO : voList) {
                RawMaterialNutritionPO po = RawMaterialNutritionMsMapper.INSTANCE.vo2po(rawMaterialNutritionVO);
//                设置创建人信息
                po.setCreateBy(loginUser.getUserId());
                boolean result = save(po);
                if (!result) {
                    throw new Exception("添加信息失败");
                }
            }
        }
//        注：这个地方时当未设置营养配料信息时也显示成功
        return BaseResult.successMsg("添加成功！");

    }
}
