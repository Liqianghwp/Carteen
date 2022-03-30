package com.diandong.mapstruct;

import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.domain.dto.NutritionAdviceDTO;
import com.diandong.domain.vo.NutritionAdviceVO;
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
public interface NutritionAdviceMsMapper {
    NutritionAdviceMsMapper INSTANCE = Mappers.getMapper(NutritionAdviceMsMapper.class);

    NutritionAdvicePO vo2po(NutritionAdviceVO vo);

    NutritionAdviceDTO po2dto(NutritionAdvicePO po);

    NutritionAdvicePO dto2po(NutritionAdviceDTO dto);

    List<NutritionAdviceDTO> poList2dtoList(List<NutritionAdvicePO> poList);

    List<NutritionAdvicePO> dtoList2poList(List<NutritionAdviceDTO> dtoList);

}
