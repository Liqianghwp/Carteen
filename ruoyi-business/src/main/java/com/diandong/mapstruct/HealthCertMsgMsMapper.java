package com.diandong.mapstruct;

import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.domain.dto.HealthCertMsgDTO;
import com.diandong.domain.vo.HealthCertMsgVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 健康证到期消息Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Mapper
public interface HealthCertMsgMsMapper {
    HealthCertMsgMsMapper INSTANCE = Mappers.getMapper(HealthCertMsgMsMapper.class);

    HealthCertMsgPO vo2po(HealthCertMsgVO vo);

    HealthCertMsgDTO po2dto(HealthCertMsgPO po);

    HealthCertMsgPO dto2po(HealthCertMsgDTO dto);

    List<HealthCertMsgDTO> poList2dtoList(List<HealthCertMsgPO> poList);

    List<HealthCertMsgPO> dtoList2poList(List<HealthCertMsgDTO> dtoList);

}
