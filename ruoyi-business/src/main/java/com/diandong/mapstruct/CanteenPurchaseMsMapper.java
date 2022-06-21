package com.diandong.mapstruct;

import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.dto.CanteenPurchaseDTO;
import com.diandong.domain.vo.CanteenPurchaseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 食堂采购Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@Mapper
public interface CanteenPurchaseMsMapper {
    CanteenPurchaseMsMapper INSTANCE = Mappers.getMapper(CanteenPurchaseMsMapper.class);

    CanteenPurchasePO vo2po(CanteenPurchaseVO vo);

    CanteenPurchaseDTO po2dto(CanteenPurchasePO po);

    CanteenPurchasePO dto2po(CanteenPurchaseDTO dto);

    List<CanteenPurchaseDTO> poList2dtoList(List<CanteenPurchasePO> poList);

    List<CanteenPurchasePO> dtoList2poList(List<CanteenPurchaseDTO> dtoList);

}
