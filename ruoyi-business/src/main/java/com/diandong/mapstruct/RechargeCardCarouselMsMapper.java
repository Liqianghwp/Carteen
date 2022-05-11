package com.diandong.mapstruct;

import com.diandong.domain.po.RechargeCardCarouselPO;
import com.diandong.domain.dto.RechargeCardCarouselDTO;
import com.diandong.domain.vo.RechargeCardCarouselVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 充值卡触摸屏轮播图Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Mapper
public interface RechargeCardCarouselMsMapper {
    RechargeCardCarouselMsMapper INSTANCE = Mappers.getMapper(RechargeCardCarouselMsMapper.class);

    RechargeCardCarouselPO vo2po(RechargeCardCarouselVO vo);

    RechargeCardCarouselDTO po2dto(RechargeCardCarouselPO po);

    RechargeCardCarouselPO dto2po(RechargeCardCarouselDTO dto);

    List<RechargeCardCarouselDTO> poList2dtoList(List<RechargeCardCarouselPO> poList);

    List<RechargeCardCarouselPO> dtoList2poList(List<RechargeCardCarouselDTO> dtoList);

}
