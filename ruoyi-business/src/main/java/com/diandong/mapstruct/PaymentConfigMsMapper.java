package com.diandong.mapstruct;

import com.diandong.domain.po.PaymentConfigPO;
import com.diandong.domain.dto.PaymentConfigDTO;
import com.diandong.domain.vo.PaymentConfigVO;
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
public interface PaymentConfigMsMapper {
    PaymentConfigMsMapper INSTANCE = Mappers.getMapper(PaymentConfigMsMapper.class);

    PaymentConfigPO vo2po(PaymentConfigVO vo);

    PaymentConfigDTO po2dto(PaymentConfigPO po);

    PaymentConfigPO dto2po(PaymentConfigDTO dto);

    List<PaymentConfigDTO> poList2dtoList(List<PaymentConfigPO> poList);

    List<PaymentConfigPO> dtoList2poList(List<PaymentConfigDTO> dtoList);

}
