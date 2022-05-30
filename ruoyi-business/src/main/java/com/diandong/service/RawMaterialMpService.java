package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.domain.vo.RawMaterialVO;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
public interface RawMaterialMpService extends CommonService<RawMaterialPO> {

    /**
     * 保存原材料信息
     * @param vo
     * @return
     */
    Boolean saveRawMaterial(RawMaterialVO vo);

    /**
     * 更新原材料信息
     * @param vo
     * @return
     */
    Boolean updateRawMaterial(RawMaterialVO vo);

    /**
     * 补充原材料营养信息
     * @param rawMaterial   原材料信息
     */
    void resetRawMaterialDTO(RawMaterialDTO rawMaterial);
}
