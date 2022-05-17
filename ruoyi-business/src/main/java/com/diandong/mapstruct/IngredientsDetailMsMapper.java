package com.diandong.mapstruct;

import com.diandong.domain.po.IngredientsDetailPO;
import com.diandong.domain.dto.IngredientsDetailDTO;
import com.diandong.domain.vo.IngredientsDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 配料管理详情Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Mapper
public interface IngredientsDetailMsMapper {
    IngredientsDetailMsMapper INSTANCE = Mappers.getMapper(IngredientsDetailMsMapper.class);

    IngredientsDetailPO vo2po(IngredientsDetailVO vo);

    IngredientsDetailDTO po2dto(IngredientsDetailPO po);

    IngredientsDetailPO dto2po(IngredientsDetailDTO dto);

    List<IngredientsDetailDTO> poList2dtoList(List<IngredientsDetailPO> poList);

    List<IngredientsDetailPO> dtoList2poList(List<IngredientsDetailDTO> dtoList);

}
