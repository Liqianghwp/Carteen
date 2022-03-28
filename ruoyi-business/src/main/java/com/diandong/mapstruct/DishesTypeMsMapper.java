package com.diandong.mapstruct;

import com.diandong.domain.dto.DishesTypeDTO;
import com.diandong.domain.po.DishesTypePO;
import com.diandong.domain.vo.DishesTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Mapper
public interface DishesTypeMsMapper {
    DishesTypeMsMapper INSTANCE = Mappers.getMapper(DishesTypeMsMapper.class);

    DishesTypePO vo2po(DishesTypeVO vo);

    DishesTypeDTO po2dto(DishesTypePO po);

    DishesTypePO dto2po(DishesTypeDTO dto);

    List<DishesTypeDTO> poList2dtoList(List<DishesTypePO> poList);

    List<DishesTypePO> dtoList2poList(List<DishesTypeDTO> dtoList);

}
