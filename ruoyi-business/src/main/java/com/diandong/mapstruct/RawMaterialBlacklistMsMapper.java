package com.diandong.mapstruct;

import com.diandong.domain.po.RawMaterialBlacklistPO;
import com.diandong.domain.dto.RawMaterialBlacklistDTO;
import com.diandong.domain.vo.RawMaterialBlacklistVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 原材料黑名单Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-13
 */
@Mapper
public interface RawMaterialBlacklistMsMapper {
    RawMaterialBlacklistMsMapper INSTANCE = Mappers.getMapper(RawMaterialBlacklistMsMapper.class);

    RawMaterialBlacklistPO vo2po(RawMaterialBlacklistVO vo);

    RawMaterialBlacklistDTO po2dto(RawMaterialBlacklistPO po);

    RawMaterialBlacklistPO dto2po(RawMaterialBlacklistDTO dto);

    List<RawMaterialBlacklistDTO> poList2dtoList(List<RawMaterialBlacklistPO> poList);

    List<RawMaterialBlacklistPO> dtoList2poList(List<RawMaterialBlacklistDTO> dtoList);

}
