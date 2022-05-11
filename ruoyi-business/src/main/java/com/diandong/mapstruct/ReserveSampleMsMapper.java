package com.diandong.mapstruct;

import com.diandong.domain.po.ReserveSamplePO;
import com.diandong.domain.dto.ReserveSampleDTO;
import com.diandong.domain.vo.ReserveSampleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 预留样品Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Mapper
public interface ReserveSampleMsMapper {
    ReserveSampleMsMapper INSTANCE = Mappers.getMapper(ReserveSampleMsMapper.class);

    ReserveSamplePO vo2po(ReserveSampleVO vo);

    ReserveSampleDTO po2dto(ReserveSamplePO po);

    ReserveSamplePO dto2po(ReserveSampleDTO dto);

    List<ReserveSampleDTO> poList2dtoList(List<ReserveSamplePO> poList);

    List<ReserveSamplePO> dtoList2poList(List<ReserveSampleDTO> dtoList);

}
