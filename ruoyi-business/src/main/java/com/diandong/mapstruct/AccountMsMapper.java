package com.diandong.mapstruct;

import com.diandong.domain.po.AccountPO;
import com.diandong.domain.dto.AccountDTO;
import com.diandong.domain.vo.AccountVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Mapper
public interface AccountMsMapper {
    AccountMsMapper INSTANCE = Mappers.getMapper(AccountMsMapper.class);

    AccountPO vo2po(AccountVO vo);

    AccountDTO po2dto(AccountPO po);

    AccountPO dto2po(AccountDTO dto);

    List<AccountDTO> poList2dtoList(List<AccountPO> poList);

    List<AccountPO> dtoList2poList(List<AccountDTO> dtoList);

}
