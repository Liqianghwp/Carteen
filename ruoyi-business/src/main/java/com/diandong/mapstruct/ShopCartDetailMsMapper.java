package com.diandong.mapstruct;

import com.diandong.domain.po.ShopCartDetailPO;
import com.diandong.domain.dto.ShopCartDetailDTO;
import com.diandong.domain.vo.ShopCartDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-04-06
 */
@Mapper
public interface ShopCartDetailMsMapper {
    ShopCartDetailMsMapper INSTANCE = Mappers.getMapper(ShopCartDetailMsMapper.class);

    ShopCartDetailPO vo2po(ShopCartDetailVO vo);

    ShopCartDetailDTO po2dto(ShopCartDetailPO po);

    ShopCartDetailPO dto2po(ShopCartDetailDTO dto);

    List<ShopCartDetailDTO> poList2dtoList(List<ShopCartDetailPO> poList);

    List<ShopCartDetailPO> dtoList2poList(List<ShopCartDetailDTO> dtoList);

}
