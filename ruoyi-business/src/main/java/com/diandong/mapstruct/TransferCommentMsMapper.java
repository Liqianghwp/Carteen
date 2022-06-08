package com.diandong.mapstruct;

import com.diandong.domain.po.TransferCommentPO;
import com.diandong.domain.dto.TransferCommentDTO;
import com.diandong.domain.vo.TransferCommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Mapper
public interface TransferCommentMsMapper {
    TransferCommentMsMapper INSTANCE = Mappers.getMapper(TransferCommentMsMapper.class);

    TransferCommentPO vo2po(TransferCommentVO vo);

    TransferCommentDTO po2dto(TransferCommentPO po);

    TransferCommentPO dto2po(TransferCommentDTO dto);

    List<TransferCommentDTO> poList2dtoList(List<TransferCommentPO> poList);

    List<TransferCommentPO> dtoList2poList(List<TransferCommentDTO> dtoList);

}
