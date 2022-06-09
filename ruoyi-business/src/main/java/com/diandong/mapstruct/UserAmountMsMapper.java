package com.diandong.mapstruct;

import com.diandong.domain.po.UserAmountPO;
import com.diandong.domain.dto.UserAmountDTO;
import com.diandong.domain.vo.UserAmountVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户金额Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Mapper
public interface UserAmountMsMapper {
    UserAmountMsMapper INSTANCE = Mappers.getMapper(UserAmountMsMapper.class);

    UserAmountPO vo2po(UserAmountVO vo);

    UserAmountDTO po2dto(UserAmountPO po);

    UserAmountPO dto2po(UserAmountDTO dto);

    List<UserAmountDTO> poList2dtoList(List<UserAmountPO> poList);

    List<UserAmountPO> dtoList2poList(List<UserAmountDTO> dtoList);

}
