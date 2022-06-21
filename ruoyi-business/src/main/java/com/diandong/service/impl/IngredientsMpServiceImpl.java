package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.IngredientsDetailPO;
import com.diandong.domain.po.IngredientsPO;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.domain.vo.IngredientsDetailVO;
import com.diandong.domain.vo.IngredientsVO;
import com.diandong.mapper.IngredientsMapper;
import com.diandong.mapstruct.IngredientsDetailMsMapper;
import com.diandong.mapstruct.IngredientsMsMapper;
import com.diandong.service.IngredientsDetailMpService;
import com.diandong.service.IngredientsMpService;
import com.diandong.service.RawMaterialMpService;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 配料管理service实现类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IngredientsMpServiceImpl extends CommonServiceImpl<IngredientsMapper, IngredientsPO>
        implements IngredientsMpService {

    @Resource
    private IngredientsDetailMpService ingredientsDetailMpService;
    @Resource
    private RawMaterialMpService rawMaterialMpService;

    @Override
    public BaseResult saveIngredients(IngredientsVO vo) {

//        保存配料管理主表信息
        IngredientsPO po = IngredientsMsMapper.INSTANCE.vo2po(vo);
        save(po);


        List<IngredientsDetailPO> list = resetIngredientDetail(vo, po);
        boolean result = ingredientsDetailMpService.saveBatch(list);

        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    @Override
    public BaseResult updateIngredients(IngredientsVO vo) {

        IngredientsPO po = IngredientsMsMapper.INSTANCE.vo2po(vo);

        List<IngredientsDetailPO> list = resetIngredientDetail(vo, po);
        ingredientsDetailMpService.saveOrUpdateBatch(list);
        boolean result = updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    private List<IngredientsDetailPO> resetIngredientDetail(IngredientsVO vo, IngredientsPO po) {

        //        主料
        List<IngredientsDetailVO> zic = vo.getZic();
//        辅料
        List<IngredientsDetailVO> zio = vo.getZio();

        List<IngredientsDetailPO> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(zic)) {
            zic.forEach(ingredientsDetailVO -> {
                ingredientsDetailVO.setType(Constants.INGREDIENTS_MAIN);
                resetIngredientDetail(po, list, ingredientsDetailVO);
            });
        }
        if (CollectionUtils.isNotEmpty(zio)) {
            zio.forEach(ingredientsDetailVO -> {
                ingredientsDetailVO.setType(Constants.INGREDIENTS_SECONDARY);
                resetIngredientDetail(po, list, ingredientsDetailVO);
            });
        }
        return list;
    }

    /**
     * 补充设置配料信息
     *
     * @param po
     * @param list
     * @param ingredientsDetailVO
     */
    private void resetIngredientDetail(IngredientsPO po, List<IngredientsDetailPO> list, IngredientsDetailVO ingredientsDetailVO) {
        ingredientsDetailVO.setIngredientsId(po.getId());
        RawMaterialPO rawMaterialPO = rawMaterialMpService.getById(ingredientsDetailVO.getRawMaterialId());

        ingredientsDetailVO.setRawMaterialName(StringUtils.isNotBlank(ingredientsDetailVO.getRawMaterialName()) ? ingredientsDetailVO.getRawMaterialName() : rawMaterialPO.getRawMaterialName());
        ingredientsDetailVO.setUnitId(Objects.nonNull(ingredientsDetailVO.getUnitId()) ? ingredientsDetailVO.getUnitId() : rawMaterialPO.getUnitId());
        ingredientsDetailVO.setUnitName(StringUtils.isNotBlank(ingredientsDetailVO.getUnitName()) ? ingredientsDetailVO.getUnitName() : rawMaterialPO.getUnitName());

        list.add(IngredientsDetailMsMapper.INSTANCE.vo2po(ingredientsDetailVO));
    }
}
