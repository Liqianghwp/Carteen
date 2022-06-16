package com.diandong.mapstruct;

import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.dto.BizDictDTO;
import com.diandong.domain.vo.BizDictVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 业务字典Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-10
 */
@Mapper
public interface BizDictMsMapper {
    BizDictMsMapper INSTANCE = Mappers.getMapper(BizDictMsMapper.class);

    BizDictPO vo2po(BizDictVO vo);

    BizDictDTO po2dto(BizDictPO po);

    BizDictPO dto2po(BizDictDTO dto);

    List<BizDictDTO> poList2dtoList(List<BizDictPO> poList);

    List<BizDictPO> dtoList2poList(List<BizDictDTO> dtoList);

}
