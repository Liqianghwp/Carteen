package com.diandong.mapstruct;

import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.dto.OpinionFeedbackDTO;
import com.diandong.domain.vo.OpinionFeedbackVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Mapper
public interface OpinionFeedbackMsMapper {
    OpinionFeedbackMsMapper INSTANCE = Mappers.getMapper(OpinionFeedbackMsMapper.class);

    OpinionFeedbackPO vo2po(OpinionFeedbackVO vo);

    OpinionFeedbackDTO po2dto(OpinionFeedbackPO po);

    OpinionFeedbackPO dto2po(OpinionFeedbackDTO dto);

    List<OpinionFeedbackDTO> poList2dtoList(List<OpinionFeedbackPO> poList);

    List<OpinionFeedbackPO> dtoList2poList(List<OpinionFeedbackDTO> dtoList);

}
