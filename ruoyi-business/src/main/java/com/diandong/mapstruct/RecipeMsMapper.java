package com.diandong.mapstruct;

import com.diandong.domain.po.RecipePO;
import com.diandong.domain.dto.RecipeDTO;
import com.diandong.domain.vo.RecipeVO;
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
public interface RecipeMsMapper {
    RecipeMsMapper INSTANCE = Mappers.getMapper(RecipeMsMapper.class);

    RecipePO vo2po(RecipeVO vo);

    RecipeDTO po2dto(RecipePO po);

    RecipePO dto2po(RecipeDTO dto);

    List<RecipeDTO> poList2dtoList(List<RecipePO> poList);

    List<RecipePO> dtoList2poList(List<RecipeDTO> dtoList);

}
