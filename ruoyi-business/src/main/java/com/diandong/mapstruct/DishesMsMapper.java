package com.diandong.mapstruct;

import com.diandong.domain.dto.DishesDTO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.vo.DishesVO;
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
public interface DishesMsMapper {
    DishesMsMapper INSTANCE = Mappers.getMapper(DishesMsMapper.class);

    DishesPO vo2po(DishesVO vo);

    DishesDTO po2dto(DishesPO po);

    DishesPO dto2po(DishesDTO dto);

    List<DishesDTO> poList2dtoList(List<DishesPO> poList);

    List<DishesPO> dtoList2poList(List<DishesDTO> dtoList);

}
