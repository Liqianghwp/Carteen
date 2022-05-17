package com.diandong.mapstruct;

import com.diandong.domain.po.IngredientsPO;
import com.diandong.domain.dto.IngredientsDTO;
import com.diandong.domain.vo.IngredientsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 配料管理Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Mapper
public interface IngredientsMsMapper {
    IngredientsMsMapper INSTANCE = Mappers.getMapper(IngredientsMsMapper.class);

    IngredientsPO vo2po(IngredientsVO vo);

    IngredientsDTO po2dto(IngredientsPO po);

    IngredientsPO dto2po(IngredientsDTO dto);

    List<IngredientsDTO> poList2dtoList(List<IngredientsPO> poList);

    List<IngredientsPO> dtoList2poList(List<IngredientsDTO> dtoList);

}
