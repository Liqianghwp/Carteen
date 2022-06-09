package com.diandong.mapstruct;

import com.diandong.domain.po.PhysicalCardPO;
import com.diandong.domain.dto.PhysicalCardDTO;
import com.diandong.domain.vo.PhysicalCardVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 实物卡Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface PhysicalCardMsMapper {
    PhysicalCardMsMapper INSTANCE = Mappers.getMapper(PhysicalCardMsMapper.class);

    PhysicalCardPO vo2po(PhysicalCardVO vo);

    PhysicalCardDTO po2dto(PhysicalCardPO po);

    PhysicalCardPO dto2po(PhysicalCardDTO dto);

    List<PhysicalCardDTO> poList2dtoList(List<PhysicalCardPO> poList);

    List<PhysicalCardPO> dtoList2poList(List<PhysicalCardDTO> dtoList);

}
