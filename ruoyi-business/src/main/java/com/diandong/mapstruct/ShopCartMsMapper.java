package com.diandong.mapstruct;

import com.diandong.domain.po.ShopCartPO;
import com.diandong.domain.dto.ShopCartDTO;
import com.diandong.domain.vo.ShopCartVO;
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
public interface ShopCartMsMapper {
    ShopCartMsMapper INSTANCE = Mappers.getMapper(ShopCartMsMapper.class);

    ShopCartPO vo2po(ShopCartVO vo);

    ShopCartDTO po2dto(ShopCartPO po);

    ShopCartPO dto2po(ShopCartDTO dto);

    List<ShopCartDTO> poList2dtoList(List<ShopCartPO> poList);

    List<ShopCartPO> dtoList2poList(List<ShopCartDTO> dtoList);

}
