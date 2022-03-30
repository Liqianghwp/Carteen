package com.diandong.mapstruct;

import com.diandong.domain.po.OrderDetailPO;
import com.diandong.domain.dto.OrderDetailDTO;
import com.diandong.domain.vo.OrderDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-30
 */
@Mapper
public interface OrderDetailMsMapper {
    OrderDetailMsMapper INSTANCE = Mappers.getMapper(OrderDetailMsMapper.class);

    OrderDetailPO vo2po(OrderDetailVO vo);

    OrderDetailDTO po2dto(OrderDetailPO po);

    OrderDetailPO dto2po(OrderDetailDTO dto);

    List<OrderDetailDTO> poList2dtoList(List<OrderDetailPO> poList);

    List<OrderDetailPO> dtoList2poList(List<OrderDetailDTO> dtoList);

}
