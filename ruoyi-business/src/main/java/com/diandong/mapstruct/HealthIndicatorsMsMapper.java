package com.diandong.mapstruct;

import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.domain.dto.HealthIndicatorsDTO;
import com.diandong.domain.vo.HealthIndicatorsVO;
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
public interface HealthIndicatorsMsMapper {
    HealthIndicatorsMsMapper INSTANCE = Mappers.getMapper(HealthIndicatorsMsMapper.class);

    HealthIndicatorsPO vo2po(HealthIndicatorsVO vo);

    HealthIndicatorsDTO po2dto(HealthIndicatorsPO po);

    HealthIndicatorsPO dto2po(HealthIndicatorsDTO dto);

    List<HealthIndicatorsDTO> poList2dtoList(List<HealthIndicatorsPO> poList);

    List<HealthIndicatorsPO> dtoList2poList(List<HealthIndicatorsDTO> dtoList);

}
