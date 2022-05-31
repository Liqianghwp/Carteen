package com.diandong.mapstruct;

import com.diandong.domain.po.InventoryLedgerPO;
import com.diandong.domain.dto.InventoryLedgerDTO;
import com.diandong.domain.vo.InventoryLedgerVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 库存台账Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface InventoryLedgerMsMapper {
    InventoryLedgerMsMapper INSTANCE = Mappers.getMapper(InventoryLedgerMsMapper.class);

    InventoryLedgerPO vo2po(InventoryLedgerVO vo);

    InventoryLedgerDTO po2dto(InventoryLedgerPO po);

    InventoryLedgerPO dto2po(InventoryLedgerDTO dto);

    List<InventoryLedgerDTO> poList2dtoList(List<InventoryLedgerPO> poList);

    List<InventoryLedgerPO> dtoList2poList(List<InventoryLedgerDTO> dtoList);

}
