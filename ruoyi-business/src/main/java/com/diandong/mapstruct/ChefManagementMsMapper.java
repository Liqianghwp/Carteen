package com.diandong.mapstruct;

import com.diandong.domain.po.ChefManagementPO;
import com.diandong.domain.dto.ChefManagementDTO;
import com.diandong.domain.vo.ChefManagementVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 厨师管理Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Mapper
public interface ChefManagementMsMapper {
    ChefManagementMsMapper INSTANCE = Mappers.getMapper(ChefManagementMsMapper.class);

    ChefManagementPO vo2po(ChefManagementVO vo);

    ChefManagementDTO po2dto(ChefManagementPO po);

    ChefManagementPO dto2po(ChefManagementDTO dto);

    List<ChefManagementDTO> poList2dtoList(List<ChefManagementPO> poList);

    List<ChefManagementPO> dtoList2poList(List<ChefManagementDTO> dtoList);

}
