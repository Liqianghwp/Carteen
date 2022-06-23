package com.diandong.mapstruct;

import com.diandong.domain.po.PurchasePlanDetailPO;
import com.diandong.domain.dto.PurchasePlanDetailDTO;
import com.diandong.domain.vo.PurchasePlanDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 采购计划单详情Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Mapper
public interface PurchasePlanDetailMsMapper {
    PurchasePlanDetailMsMapper INSTANCE = Mappers.getMapper(PurchasePlanDetailMsMapper.class);

    PurchasePlanDetailPO vo2po(PurchasePlanDetailVO vo);

    PurchasePlanDetailDTO po2dto(PurchasePlanDetailPO po);

    PurchasePlanDetailPO dto2po(PurchasePlanDetailDTO dto);

    List<PurchasePlanDetailDTO> poList2dtoList(List<PurchasePlanDetailPO> poList);

    List<PurchasePlanDetailPO> dtoList2poList(List<PurchasePlanDetailDTO> dtoList);

}
