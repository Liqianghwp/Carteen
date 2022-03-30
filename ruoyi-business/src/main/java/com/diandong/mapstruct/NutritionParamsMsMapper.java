package com.diandong.mapstruct;

import com.diandong.domain.po.NutritionParamsPO;
import com.diandong.domain.dto.NutritionParamsDTO;
import com.diandong.domain.vo.NutritionParamsVO;
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
public interface NutritionParamsMsMapper {
    NutritionParamsMsMapper INSTANCE = Mappers.getMapper(NutritionParamsMsMapper.class);

    NutritionParamsPO vo2po(NutritionParamsVO vo);

    NutritionParamsDTO po2dto(NutritionParamsPO po);

    NutritionParamsPO dto2po(NutritionParamsDTO dto);

    List<NutritionParamsDTO> poList2dtoList(List<NutritionParamsPO> poList);

    List<NutritionParamsPO> dtoList2poList(List<NutritionParamsDTO> dtoList);

}
