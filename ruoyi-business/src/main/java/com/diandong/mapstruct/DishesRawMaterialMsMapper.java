package com.diandong.mapstruct;

import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.vo.DishesRawMaterialVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Mapper
public interface DishesRawMaterialMsMapper {
    DishesRawMaterialMsMapper INSTANCE = Mappers.getMapper(DishesRawMaterialMsMapper.class);

    DishesRawMaterialPO vo2po(DishesRawMaterialVO vo);

    DishesRawMaterialDTO po2dto(DishesRawMaterialPO po);

    DishesRawMaterialPO dto2po(DishesRawMaterialDTO dto);

    List<DishesRawMaterialDTO> poList2dtoList(List<DishesRawMaterialPO> poList);

    List<DishesRawMaterialPO> dtoList2poList(List<DishesRawMaterialDTO> dtoList);

}
