package com.diandong.mapstruct;

import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.dto.BusinessConfigDTO;
import com.diandong.domain.vo.BusinessConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统配置Mapstruct
 *
 * @author YuLiu
 * @date 2022-03-23
 */
@Mapper
public interface BusinessConfigMsMapper {
    BusinessConfigMsMapper INSTANCE = Mappers.getMapper(BusinessConfigMsMapper.class);

    BusinessConfigPO vo2po(BusinessConfigVO vo);

    BusinessConfigDTO po2dto(BusinessConfigPO po);

    BusinessConfigPO dto2po(BusinessConfigDTO dto);

    List<BusinessConfigDTO> poList2dtoList(List<BusinessConfigPO> poList);

    List<BusinessConfigPO> dtoList2poList(List<BusinessConfigDTO> dtoList);

}
