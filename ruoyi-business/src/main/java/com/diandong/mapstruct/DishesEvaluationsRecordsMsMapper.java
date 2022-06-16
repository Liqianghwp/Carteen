package com.diandong.mapstruct;

import com.diandong.domain.po.DishesEvaluationsRecordsPO;
import com.diandong.domain.dto.DishesEvaluationsRecordsDTO;
import com.diandong.domain.vo.DishesEvaluationsRecordsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜品评价记录Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Mapper
public interface DishesEvaluationsRecordsMsMapper {
    DishesEvaluationsRecordsMsMapper INSTANCE = Mappers.getMapper(DishesEvaluationsRecordsMsMapper.class);

    DishesEvaluationsRecordsPO vo2po(DishesEvaluationsRecordsVO vo);

    DishesEvaluationsRecordsDTO po2dto(DishesEvaluationsRecordsPO po);

    DishesEvaluationsRecordsPO dto2po(DishesEvaluationsRecordsDTO dto);

    List<DishesEvaluationsRecordsDTO> poList2dtoList(List<DishesEvaluationsRecordsPO> poList);

    List<DishesEvaluationsRecordsPO> dtoList2poList(List<DishesEvaluationsRecordsDTO> dtoList);

}
