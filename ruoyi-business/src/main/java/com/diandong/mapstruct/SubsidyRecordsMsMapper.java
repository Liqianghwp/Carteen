package com.diandong.mapstruct;

import com.diandong.domain.po.SubsidyRecordsPO;
import com.diandong.domain.dto.SubsidyRecordsDTO;
import com.diandong.domain.vo.SubsidyRecordsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 补贴记录Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface SubsidyRecordsMsMapper {
    SubsidyRecordsMsMapper INSTANCE = Mappers.getMapper(SubsidyRecordsMsMapper.class);

    SubsidyRecordsPO vo2po(SubsidyRecordsVO vo);

    SubsidyRecordsDTO po2dto(SubsidyRecordsPO po);

    SubsidyRecordsPO dto2po(SubsidyRecordsDTO dto);

    List<SubsidyRecordsDTO> poList2dtoList(List<SubsidyRecordsPO> poList);

    List<SubsidyRecordsPO> dtoList2poList(List<SubsidyRecordsDTO> dtoList);

}
