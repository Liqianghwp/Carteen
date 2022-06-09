package com.diandong.mapstruct;

import com.diandong.domain.po.RechargeTimesConfigPO;
import com.diandong.domain.dto.RechargeTimesConfigDTO;
import com.diandong.domain.vo.RechargeTimesConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 充值次数设置Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface RechargeTimesConfigMsMapper {
    RechargeTimesConfigMsMapper INSTANCE = Mappers.getMapper(RechargeTimesConfigMsMapper.class);

    RechargeTimesConfigPO vo2po(RechargeTimesConfigVO vo);

    RechargeTimesConfigDTO po2dto(RechargeTimesConfigPO po);

    RechargeTimesConfigPO dto2po(RechargeTimesConfigDTO dto);

    List<RechargeTimesConfigDTO> poList2dtoList(List<RechargeTimesConfigPO> poList);

    List<RechargeTimesConfigPO> dtoList2poList(List<RechargeTimesConfigDTO> dtoList);

}
