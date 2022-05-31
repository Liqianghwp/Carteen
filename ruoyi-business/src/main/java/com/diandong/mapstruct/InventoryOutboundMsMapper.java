package com.diandong.mapstruct;

import com.diandong.domain.po.InventoryOutboundPO;
import com.diandong.domain.dto.InventoryOutboundDTO;
import com.diandong.domain.vo.InventoryOutboundVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 出库Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface InventoryOutboundMsMapper {
    InventoryOutboundMsMapper INSTANCE = Mappers.getMapper(InventoryOutboundMsMapper.class);

    InventoryOutboundPO vo2po(InventoryOutboundVO vo);

    InventoryOutboundDTO po2dto(InventoryOutboundPO po);

    InventoryOutboundPO dto2po(InventoryOutboundDTO dto);

    List<InventoryOutboundDTO> poList2dtoList(List<InventoryOutboundPO> poList);

    List<InventoryOutboundPO> dtoList2poList(List<InventoryOutboundDTO> dtoList);

}
