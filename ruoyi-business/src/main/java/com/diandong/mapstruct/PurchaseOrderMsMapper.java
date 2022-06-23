package com.diandong.mapstruct;

import com.diandong.domain.po.PurchaseOrderPO;
import com.diandong.domain.dto.PurchaseOrderDTO;
import com.diandong.domain.vo.PurchaseOrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 采购订单管理Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Mapper
public interface PurchaseOrderMsMapper {
    PurchaseOrderMsMapper INSTANCE = Mappers.getMapper(PurchaseOrderMsMapper.class);

    PurchaseOrderPO vo2po(PurchaseOrderVO vo);

    PurchaseOrderDTO po2dto(PurchaseOrderPO po);

    PurchaseOrderPO dto2po(PurchaseOrderDTO dto);

    List<PurchaseOrderDTO> poList2dtoList(List<PurchaseOrderPO> poList);

    List<PurchaseOrderPO> dtoList2poList(List<PurchaseOrderDTO> dtoList);

}
