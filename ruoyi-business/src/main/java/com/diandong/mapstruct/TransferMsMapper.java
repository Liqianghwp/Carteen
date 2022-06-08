package com.diandong.mapstruct;

import com.diandong.domain.po.TransferPO;
import com.diandong.domain.dto.TransferDTO;
import com.diandong.domain.vo.TransferVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Mapper
public interface TransferMsMapper {
    TransferMsMapper INSTANCE = Mappers.getMapper(TransferMsMapper.class);

    TransferPO vo2po(TransferVO vo);

    TransferDTO po2dto(TransferPO po);

    TransferPO dto2po(TransferDTO dto);

    List<TransferDTO> poList2dtoList(List<TransferPO> poList);

    List<TransferPO> dtoList2poList(List<TransferDTO> dtoList);

}
