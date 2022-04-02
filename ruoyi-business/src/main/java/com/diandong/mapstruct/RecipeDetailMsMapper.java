package com.diandong.mapstruct;

import com.diandong.domain.po.RecipeDetailPO;
import com.diandong.domain.dto.RecipeDetailDTO;
import com.diandong.domain.vo.RecipeDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@Mapper
public interface RecipeDetailMsMapper {
    RecipeDetailMsMapper INSTANCE = Mappers.getMapper(RecipeDetailMsMapper.class);

    RecipeDetailPO vo2po(RecipeDetailVO vo);

    RecipeDetailDTO po2dto(RecipeDetailPO po);

    RecipeDetailPO dto2po(RecipeDetailDTO dto);

    List<RecipeDetailDTO> poList2dtoList(List<RecipeDetailPO> poList);

    List<RecipeDetailPO> dtoList2poList(List<RecipeDetailDTO> dtoList);

}
