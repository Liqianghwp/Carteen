package com.diandong.mapstruct;

import com.diandong.domain.po.PayRecordsPO;
import com.diandong.domain.dto.PayRecordsDTO;
import com.diandong.domain.vo.PayRecordsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 支付记录Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-17
 */
@Mapper
public interface PayRecordsMsMapper {
    PayRecordsMsMapper INSTANCE = Mappers.getMapper(PayRecordsMsMapper.class);

    PayRecordsPO vo2po(PayRecordsVO vo);

    PayRecordsDTO po2dto(PayRecordsPO po);

    PayRecordsPO dto2po(PayRecordsDTO dto);

    List<PayRecordsDTO> poList2dtoList(List<PayRecordsPO> poList);

    List<PayRecordsPO> dtoList2poList(List<PayRecordsDTO> dtoList);

}
