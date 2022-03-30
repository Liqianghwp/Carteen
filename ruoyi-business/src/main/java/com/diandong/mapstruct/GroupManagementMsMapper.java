package com.diandong.mapstruct;

import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.dto.GroupManagementDTO;
import com.diandong.domain.vo.GroupManagementVO;
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
public interface GroupManagementMsMapper {
    GroupManagementMsMapper INSTANCE = Mappers.getMapper(GroupManagementMsMapper.class);

    GroupManagementPO vo2po(GroupManagementVO vo);

    GroupManagementDTO po2dto(GroupManagementPO po);

    GroupManagementPO dto2po(GroupManagementDTO dto);

    List<GroupManagementDTO> poList2dtoList(List<GroupManagementPO> poList);

    List<GroupManagementPO> dtoList2poList(List<GroupManagementDTO> dtoList);

}
