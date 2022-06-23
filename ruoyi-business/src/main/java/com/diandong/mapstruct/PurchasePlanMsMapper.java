package com.diandong.mapstruct;

import com.diandong.domain.po.PurchasePlanPO;
import com.diandong.domain.dto.PurchasePlanDTO;
import com.diandong.domain.vo.PurchasePlanVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 采购计划单Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Mapper
public interface PurchasePlanMsMapper {
    PurchasePlanMsMapper INSTANCE = Mappers.getMapper(PurchasePlanMsMapper.class);

    PurchasePlanPO vo2po(PurchasePlanVO vo);

    PurchasePlanDTO po2dto(PurchasePlanPO po);

    PurchasePlanPO dto2po(PurchasePlanDTO dto);

    List<PurchasePlanDTO> poList2dtoList(List<PurchasePlanPO> poList);

    List<PurchasePlanPO> dtoList2poList(List<PurchasePlanDTO> dtoList);

}
