package com.diandong.mapstruct;

import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.dto.DishesNutritionDTO;
import com.diandong.domain.vo.DishesNutritionVO;
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
public interface DishesNutritionMsMapper {
    DishesNutritionMsMapper INSTANCE = Mappers.getMapper(DishesNutritionMsMapper.class);

    DishesNutritionPO vo2po(DishesNutritionVO vo);

    DishesNutritionDTO po2dto(DishesNutritionPO po);

    DishesNutritionPO dto2po(DishesNutritionDTO dto);

    List<DishesNutritionDTO> poList2dtoList(List<DishesNutritionPO> poList);

    List<DishesNutritionPO> dtoList2poList(List<DishesNutritionDTO> dtoList);

}
