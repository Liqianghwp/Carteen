package com.diandong.mapstruct;

import com.diandong.domain.po.PurchasingPO;
import com.diandong.domain.dto.PurchasingDTO;
import com.diandong.domain.vo.PurchasingVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 采购清单管理Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Mapper
public interface PurchasingMsMapper {
    PurchasingMsMapper INSTANCE = Mappers.getMapper(PurchasingMsMapper.class);

    PurchasingPO vo2po(PurchasingVO vo);

    PurchasingDTO po2dto(PurchasingPO po);

    PurchasingPO dto2po(PurchasingDTO dto);

    List<PurchasingDTO> poList2dtoList(List<PurchasingPO> poList);

    List<PurchasingPO> dtoList2poList(List<PurchasingDTO> dtoList);

}
