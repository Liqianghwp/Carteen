package com.diandong.mapstruct;

import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.domain.dto.HealthCertificateDTO;
import com.diandong.domain.vo.HealthCertificateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 健康证Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Mapper
public interface HealthCertificateMsMapper {
    HealthCertificateMsMapper INSTANCE = Mappers.getMapper(HealthCertificateMsMapper.class);

    HealthCertificatePO vo2po(HealthCertificateVO vo);

    HealthCertificateDTO po2dto(HealthCertificatePO po);

    HealthCertificatePO dto2po(HealthCertificateDTO dto);

    List<HealthCertificateDTO> poList2dtoList(List<HealthCertificatePO> poList);

    List<HealthCertificatePO> dtoList2poList(List<HealthCertificateDTO> dtoList);

}
