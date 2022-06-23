package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.dto.RawMaterialNutritionDTO;
import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.domain.vo.RawMaterialNutritionVO;
import com.diandong.domain.vo.RawMaterialVO;
import com.diandong.mapper.RawMaterialMapper;
import com.diandong.mapstruct.RawMaterialMsMapper;
import com.diandong.mapstruct.RawMaterialNutritionMsMapper;
import com.diandong.service.BizDictMpService;
import com.diandong.service.CanteenMpService;
import com.diandong.service.RawMaterialMpService;
import com.diandong.service.RawMaterialNutritionMpService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RawMaterialMpServiceImpl extends CommonServiceImpl<RawMaterialMapper, RawMaterialPO>
        implements RawMaterialMpService {

    @Resource
    private RawMaterialNutritionMpService rawMaterialNutritionMpService;
//    @Resource
//    private ISysDictDataService dictDataService;

    @Resource
    private BizDictMpService bizDictMpService;
    @Resource
    private CanteenMpService canteenMpService;


    @Override
    public Boolean saveRawMaterial(RawMaterialVO vo) {
        RawMaterialPO rawMaterialPO = RawMaterialMsMapper.INSTANCE.vo2po(vo);

//        SysDictData categoryData = dictDataService.selectDictDataById(vo.getCategoryId());

        BizDictPO bizDictPO = bizDictMpService.getById(vo.getCategoryId());

        CanteenPO canteenPO = canteenMpService.getById(SecurityUtils.getCanteenId());
        rawMaterialPO.setCanteenName(canteenPO.getCanteenName());
        rawMaterialPO.setCategoryName(bizDictPO.getDictLabel());
        boolean result = save(rawMaterialPO);
        if (result) {
            List<RawMaterialNutritionVO> rawMaterialNutritionVOList = vo.getRawMaterialNutritionVOList();

            if (CollectionUtils.isNotEmpty(rawMaterialNutritionVOList)) {
                result = saveRawMaterialNutrition(rawMaterialNutritionVOList, rawMaterialPO);
            }
        }
        return result;
    }

    @Override
    public Boolean updateRawMaterial(RawMaterialVO vo) {

        RawMaterialPO rawMaterialPO = RawMaterialMsMapper.INSTANCE.vo2po(vo);

        boolean result = updateById(rawMaterialPO);
        List<RawMaterialNutritionVO> rawMaterialNutritionVOList = vo.getRawMaterialNutritionVOList();
        if (CollectionUtils.isNotEmpty(rawMaterialNutritionVOList)) {
            LambdaQueryWrapper<RawMaterialNutritionPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RawMaterialNutritionPO::getRawMaterialId, rawMaterialPO.getId());
            rawMaterialNutritionMpService.remove(wrapper);

            result = saveRawMaterialNutrition(rawMaterialNutritionVOList, rawMaterialPO);
        }


        return result;
    }

    @Override
    public void resetRawMaterialDTO(RawMaterialDTO rawMaterial) {
        List<RawMaterialNutritionPO> list = rawMaterialNutritionMpService.lambdaQuery()
                .eq(RawMaterialNutritionPO::getRawMaterialId, rawMaterial.getId())
                .eq(RawMaterialNutritionPO::getDelFlag, Constants.DEL_NO)
                .list();

        List<RawMaterialNutritionDTO> rawMaterialNutritionDTOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(rawMaterialNutritionPO -> rawMaterialNutritionDTOList.add(RawMaterialNutritionMsMapper.INSTANCE.po2dto(rawMaterialNutritionPO)));
        }

        rawMaterial.setRawMaterialNutritionDTOList(rawMaterialNutritionDTOList);

    }

    private Boolean saveRawMaterialNutrition(List<RawMaterialNutritionVO> rawMaterialNutritionVOList, RawMaterialPO rawMaterialPO) {
        //            保存新的数据
        List<RawMaterialNutritionPO> rawMaterialNutritionPOList = new ArrayList<>();
        rawMaterialNutritionVOList.forEach(rawMaterialNutritionVO -> {

            RawMaterialNutritionPO rawMaterialNutritionPO = RawMaterialNutritionMsMapper.INSTANCE.vo2po(rawMaterialNutritionVO);

            rawMaterialNutritionPO.setRawMaterialId(rawMaterialPO.getId());
            rawMaterialNutritionPO.setRawMaterialName(rawMaterialPO.getRawMaterialName());

            rawMaterialNutritionPOList.add(rawMaterialNutritionPO);

        });
        return rawMaterialNutritionMpService.saveBatch(rawMaterialNutritionPOList);
    }

}
