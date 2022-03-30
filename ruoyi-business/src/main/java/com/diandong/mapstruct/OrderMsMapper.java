package com.diandong.mapstruct;

import com.diandong.domain.po.OrderPO;
import com.diandong.domain.dto.OrderDTO;
import com.diandong.domain.vo.OrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Mapper
public interface OrderMsMapper {
    OrderMsMapper INSTANCE = Mappers.getMapper(OrderMsMapper.class);

    OrderPO vo2po(OrderVO vo);

    OrderDTO po2dto(OrderPO po);

    OrderPO dto2po(OrderDTO dto);

    List<OrderDTO> poList2dtoList(List<OrderPO> poList);

    List<OrderPO> dtoList2poList(List<OrderDTO> dtoList);

}
