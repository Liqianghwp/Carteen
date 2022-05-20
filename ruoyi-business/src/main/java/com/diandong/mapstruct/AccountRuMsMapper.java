package com.diandong.mapstruct;

import com.diandong.domain.po.AccountRuPO;
import com.diandong.domain.dto.AccountRuDTO;
import com.diandong.domain.vo.AccountRuVO;
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
public interface AccountRuMsMapper {
    AccountRuMsMapper INSTANCE = Mappers.getMapper(AccountRuMsMapper.class);

    AccountRuPO vo2po(AccountRuVO vo);

    AccountRuDTO po2dto(AccountRuPO po);

    AccountRuPO dto2po(AccountRuDTO dto);

    List<AccountRuDTO> poList2dtoList(List<AccountRuPO> poList);

    List<AccountRuPO> dtoList2poList(List<AccountRuDTO> dtoList);

}
