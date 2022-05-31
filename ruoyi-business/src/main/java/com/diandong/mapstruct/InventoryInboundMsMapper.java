package com.diandong.mapstruct;

import com.diandong.domain.po.InventoryInboundPO;
import com.diandong.domain.dto.InventoryInboundDTO;
import com.diandong.domain.vo.InventoryInboundVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 入库Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface InventoryInboundMsMapper {
    InventoryInboundMsMapper INSTANCE = Mappers.getMapper(InventoryInboundMsMapper.class);

    InventoryInboundPO vo2po(InventoryInboundVO vo);

    InventoryInboundDTO po2dto(InventoryInboundPO po);

    InventoryInboundPO dto2po(InventoryInboundDTO dto);

    List<InventoryInboundDTO> poList2dtoList(List<InventoryInboundPO> poList);

    List<InventoryInboundPO> dtoList2poList(List<InventoryInboundDTO> dtoList);

}
