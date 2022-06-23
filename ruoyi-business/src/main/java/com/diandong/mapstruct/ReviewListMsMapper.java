package com.diandong.mapstruct;

import com.diandong.domain.po.ReviewListPO;
import com.diandong.domain.dto.ReviewListDTO;
import com.diandong.domain.vo.ReviewListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 审核列Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Mapper
public interface ReviewListMsMapper {
    ReviewListMsMapper INSTANCE = Mappers.getMapper(ReviewListMsMapper.class);

    ReviewListPO vo2po(ReviewListVO vo);

    ReviewListDTO po2dto(ReviewListPO po);

    ReviewListPO dto2po(ReviewListDTO dto);

    List<ReviewListDTO> poList2dtoList(List<ReviewListPO> poList);

    List<ReviewListPO> dtoList2poList(List<ReviewListDTO> dtoList);

}
