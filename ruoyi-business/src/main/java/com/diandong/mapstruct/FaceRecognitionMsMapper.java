package com.diandong.mapstruct;

import com.diandong.domain.po.FaceRecognitionPO;
import com.diandong.domain.dto.FaceRecognitionDTO;
import com.diandong.domain.vo.FaceRecognitionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapstruct
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Mapper
public interface FaceRecognitionMsMapper {
    FaceRecognitionMsMapper INSTANCE = Mappers.getMapper(FaceRecognitionMsMapper.class);

    FaceRecognitionPO vo2po(FaceRecognitionVO vo);

    FaceRecognitionDTO po2dto(FaceRecognitionPO po);

    FaceRecognitionPO dto2po(FaceRecognitionDTO dto);

    List<FaceRecognitionDTO> poList2dtoList(List<FaceRecognitionPO> poList);

    List<FaceRecognitionPO> dtoList2poList(List<FaceRecognitionDTO> dtoList);

}
