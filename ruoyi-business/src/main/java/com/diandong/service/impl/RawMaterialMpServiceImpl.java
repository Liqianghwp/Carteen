package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.dto.RawMaterialNutritionDTO;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.mapper.RawMaterialMapper;
import com.diandong.mapstruct.RawMaterialNutritionMsMapper;
import com.diandong.service.RawMaterialMpService;
import com.diandong.service.RawMaterialNutritionMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RawMaterialMpServiceImpl extends CommonServiceImpl<RawMaterialMapper, RawMaterialPO>
        implements RawMaterialMpService {


    @Resource
    private RawMaterialNutritionMpService rawMaterialNutritionMpService;

    @Override
    public void resetRawMaterialDTO(RawMaterialDTO rawMaterial) {
        List<RawMaterialNutritionPO> list = rawMaterialNutritionMpService.lambdaQuery()
                .eq(RawMaterialNutritionPO::getRawMaterialId, rawMaterial.getId())
                .eq(RawMaterialNutritionPO::getDelFlag, Constants.DEL_NO)
                .list();

        List<RawMaterialNutritionDTO> rawMaterialNutritionDTOList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(rawMaterialNutritionPO -> rawMaterialNutritionDTOList.add(RawMaterialNutritionMsMapper.INSTANCE.po2dto(rawMaterialNutritionPO)));
        }

        rawMaterial.setRawMaterialNutritionDTOList(rawMaterialNutritionDTOList);

    }
}
