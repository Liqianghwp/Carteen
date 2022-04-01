package com.diandong.mapstruct;

import com.diandong.domain.po.CommentPO;
import com.diandong.domain.dto.CommentDTO;
import com.diandong.domain.vo.CommentVO;
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
public interface CommentMsMapper {
    CommentMsMapper INSTANCE = Mappers.getMapper(CommentMsMapper.class);

    CommentPO vo2po(CommentVO vo);

    CommentDTO po2dto(CommentPO po);

    CommentPO dto2po(CommentDTO dto);

    List<CommentDTO> poList2dtoList(List<CommentPO> poList);

    List<CommentPO> dtoList2poList(List<CommentDTO> dtoList);

}
