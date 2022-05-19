package com.diandong.mapstruct;

import com.diandong.domain.po.SupplierPO;
import com.diandong.domain.dto.SupplierDTO;
import com.diandong.domain.vo.SupplierVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 供应商管理Mapstruct
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@Mapper
public interface SupplierMsMapper {
    SupplierMsMapper INSTANCE = Mappers.getMapper(SupplierMsMapper.class);

    SupplierPO vo2po(SupplierVO vo);

    SupplierDTO po2dto(SupplierPO po);

    SupplierPO dto2po(SupplierDTO dto);

    List<SupplierDTO> poList2dtoList(List<SupplierPO> poList);

    List<SupplierPO> dtoList2poList(List<SupplierDTO> dtoList);

}
