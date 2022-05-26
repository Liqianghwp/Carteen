package com.diandong.mapstruct;

import com.diandong.domain.po.DishesSupplierPO;
import com.diandong.domain.dto.DishesSupplierDTO;
import com.diandong.domain.vo.DishesSupplierVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜品供应商信息Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Mapper
public interface DishesSupplierMsMapper {
    DishesSupplierMsMapper INSTANCE = Mappers.getMapper(DishesSupplierMsMapper.class);

    DishesSupplierPO vo2po(DishesSupplierVO vo);

    DishesSupplierDTO po2dto(DishesSupplierPO po);

    DishesSupplierPO dto2po(DishesSupplierDTO dto);

    List<DishesSupplierDTO> poList2dtoList(List<DishesSupplierPO> poList);

    List<DishesSupplierPO> dtoList2poList(List<DishesSupplierDTO> dtoList);

}
