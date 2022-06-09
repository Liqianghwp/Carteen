package com.diandong.mapstruct;

import com.diandong.domain.po.RechargeTimesRecordsPO;
import com.diandong.domain.dto.RechargeTimesRecordsDTO;
import com.diandong.domain.vo.RechargeTimesRecordsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 充次记录Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Mapper
public interface RechargeTimesRecordsMsMapper {
    RechargeTimesRecordsMsMapper INSTANCE = Mappers.getMapper(RechargeTimesRecordsMsMapper.class);

    RechargeTimesRecordsPO vo2po(RechargeTimesRecordsVO vo);

    RechargeTimesRecordsDTO po2dto(RechargeTimesRecordsPO po);

    RechargeTimesRecordsPO dto2po(RechargeTimesRecordsDTO dto);

    List<RechargeTimesRecordsDTO> poList2dtoList(List<RechargeTimesRecordsPO> poList);

    List<RechargeTimesRecordsPO> dtoList2poList(List<RechargeTimesRecordsDTO> dtoList);

}
