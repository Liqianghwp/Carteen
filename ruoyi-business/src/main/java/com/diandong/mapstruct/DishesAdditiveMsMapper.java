package com.diandong.mapstruct;

import com.diandong.domain.po.DishesAdditivePO;
import com.diandong.domain.dto.DishesAdditiveDTO;
import com.diandong.domain.vo.DishesAdditiveVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜品添加剂信息Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Mapper
public interface DishesAdditiveMsMapper {
    DishesAdditiveMsMapper INSTANCE = Mappers.getMapper(DishesAdditiveMsMapper.class);

    DishesAdditivePO vo2po(DishesAdditiveVO vo);

    DishesAdditiveDTO po2dto(DishesAdditivePO po);

    DishesAdditivePO dto2po(DishesAdditiveDTO dto);

    List<DishesAdditiveDTO> poList2dtoList(List<DishesAdditivePO> poList);

    List<DishesAdditivePO> dtoList2poList(List<DishesAdditiveDTO> dtoList);

}
