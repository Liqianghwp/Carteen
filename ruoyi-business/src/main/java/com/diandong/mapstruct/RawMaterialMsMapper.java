package com.diandong.mapstruct;

import com.diandong.domain.po.RawMaterialPO;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.vo.RawMaterialVO;
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
public interface RawMaterialMsMapper {
    RawMaterialMsMapper INSTANCE = Mappers.getMapper(RawMaterialMsMapper.class);

    RawMaterialPO vo2po(RawMaterialVO vo);

    RawMaterialDTO po2dto(RawMaterialPO po);

    RawMaterialPO dto2po(RawMaterialDTO dto);

    List<RawMaterialDTO> poList2dtoList(List<RawMaterialPO> poList);

    List<RawMaterialPO> dtoList2poList(List<RawMaterialDTO> dtoList);

}
