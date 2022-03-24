package com.diandong.mapstruct;

import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.dto.CanteenDTO;
import com.diandong.domain.vo.CanteenVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-23
 */
@Mapper
public interface CanteenMsMapper {
    CanteenMsMapper INSTANCE = Mappers.getMapper(CanteenMsMapper.class);

    CanteenPO vo2po(CanteenVO vo);

    CanteenDTO po2dto(CanteenPO po);

    CanteenPO dto2po(CanteenDTO dto);

    List<CanteenDTO> poList2dtoList(List<CanteenPO> poList);

    List<CanteenPO> dtoList2poList(List<CanteenDTO> dtoList);

}
