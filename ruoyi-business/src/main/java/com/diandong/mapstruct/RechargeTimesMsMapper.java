package com.diandong.mapstruct;

import com.diandong.domain.po.RechargeTimesPO;
import com.diandong.domain.dto.RechargeTimesDTO;
import com.diandong.domain.vo.RechargeTimesVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 充值次数设置Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Mapper
public interface RechargeTimesMsMapper {
    RechargeTimesMsMapper INSTANCE = Mappers.getMapper(RechargeTimesMsMapper.class);

    RechargeTimesPO vo2po(RechargeTimesVO vo);

    RechargeTimesDTO po2dto(RechargeTimesPO po);

    RechargeTimesPO dto2po(RechargeTimesDTO dto);

    List<RechargeTimesDTO> poList2dtoList(List<RechargeTimesPO> poList);

    List<RechargeTimesPO> dtoList2poList(List<RechargeTimesDTO> dtoList);

}
