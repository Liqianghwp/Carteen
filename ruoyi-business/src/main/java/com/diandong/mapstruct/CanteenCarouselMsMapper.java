package com.diandong.mapstruct;

import com.diandong.domain.po.CanteenCarouselPO;
import com.diandong.domain.dto.CanteenCarouselDTO;
import com.diandong.domain.vo.CanteenCarouselVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 食堂轮播图Mapstruct
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Mapper
public interface CanteenCarouselMsMapper {
    CanteenCarouselMsMapper INSTANCE = Mappers.getMapper(CanteenCarouselMsMapper.class);

    CanteenCarouselPO vo2po(CanteenCarouselVO vo);

    CanteenCarouselDTO po2dto(CanteenCarouselPO po);

    CanteenCarouselPO dto2po(CanteenCarouselDTO dto);

    List<CanteenCarouselDTO> poList2dtoList(List<CanteenCarouselPO> poList);

    List<CanteenCarouselPO> dtoList2poList(List<CanteenCarouselDTO> dtoList);

}
