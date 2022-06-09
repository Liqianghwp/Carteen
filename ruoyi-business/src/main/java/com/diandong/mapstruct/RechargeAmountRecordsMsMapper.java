package com.diandong.mapstruct;

import com.diandong.domain.po.RechargeAmountRecordsPO;
import com.diandong.domain.dto.RechargeAmountRecordsDTO;
import com.diandong.domain.vo.RechargeAmountRecordsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 充值记录Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Mapper
public interface RechargeAmountRecordsMsMapper {
    RechargeAmountRecordsMsMapper INSTANCE = Mappers.getMapper(RechargeAmountRecordsMsMapper.class);

    RechargeAmountRecordsPO vo2po(RechargeAmountRecordsVO vo);

    RechargeAmountRecordsDTO po2dto(RechargeAmountRecordsPO po);

    RechargeAmountRecordsPO dto2po(RechargeAmountRecordsDTO dto);

    List<RechargeAmountRecordsDTO> poList2dtoList(List<RechargeAmountRecordsPO> poList);

    List<RechargeAmountRecordsPO> dtoList2poList(List<RechargeAmountRecordsDTO> dtoList);

}
