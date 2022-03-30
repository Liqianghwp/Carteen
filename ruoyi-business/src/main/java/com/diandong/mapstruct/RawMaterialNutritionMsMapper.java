package com.diandong.mapstruct;

import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.dto.RawMaterialNutritionDTO;
import com.diandong.domain.vo.RawMaterialNutritionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Mapper
public interface RawMaterialNutritionMsMapper {
    RawMaterialNutritionMsMapper INSTANCE = Mappers.getMapper(RawMaterialNutritionMsMapper.class);

    RawMaterialNutritionPO vo2po(RawMaterialNutritionVO vo);

    RawMaterialNutritionDTO po2dto(RawMaterialNutritionPO po);

    RawMaterialNutritionPO dto2po(RawMaterialNutritionDTO dto);

    List<RawMaterialNutritionDTO> poList2dtoList(List<RawMaterialNutritionPO> poList);

    List<RawMaterialNutritionPO> dtoList2poList(List<RawMaterialNutritionDTO> dtoList);

}
