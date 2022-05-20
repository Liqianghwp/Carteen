package com.diandong.mapstruct;

import com.diandong.domain.po.AccountChuPO;
import com.diandong.domain.dto.AccountChuDTO;
import com.diandong.domain.vo.AccountChuVO;
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
public interface AccountChuMsMapper {
    AccountChuMsMapper INSTANCE = Mappers.getMapper(AccountChuMsMapper.class);

    AccountChuPO vo2po(AccountChuVO vo);

    AccountChuDTO po2dto(AccountChuPO po);

    AccountChuPO dto2po(AccountChuDTO dto);

    List<AccountChuDTO> poList2dtoList(List<AccountChuPO> poList);

    List<AccountChuPO> dtoList2poList(List<AccountChuDTO> dtoList);

}
