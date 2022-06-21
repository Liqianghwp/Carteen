package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.vo.BizDictVO;
import com.diandong.mapper.BizDictMapper;
import com.diandong.mapstruct.BizDictMsMapper;
import com.diandong.service.BizDictMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.pinyin.ChineseCharacterUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 业务字典service实现类
 *
 * @author YuLiu
 * @date 2022-06-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BizDictMpServiceImpl extends CommonServiceImpl<BizDictMapper, BizDictPO>
        implements BizDictMpService {


    @Override
    public BaseResult pageList(BizDictVO vo) {
        Page<BizDictPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    @Override
    public BaseResult saveBizDict(BizDictVO vo) {


//        List<BizDictPO> list = lambdaQuery().eq(BizDictPO::getDelFlag, Constants.DEL_NO).eq(BizDictPO::getDictType, vo.getDictType()).eq(BizDictPO::getDictLabel, vo.getDictLabel()).list();
//        if(CollectionUtils.isNotEmpty(list)){
//            return BaseResult.error("同一个类型下不能");
//        }

        BizDictPO bizDictPO = BizDictMsMapper.INSTANCE.vo2po(vo);

        if (Objects.isNull(bizDictPO.getDictSort())) {
            BizDictPO maxSortDict = lambdaQuery().eq(BizDictPO::getDictType, vo.getDictType())
                    .orderByDesc(BizDictPO::getDictSort)
                    .last("limit 1").one();

            bizDictPO.setDictSort(Objects.isNull(maxSortDict) ? 0 : maxSortDict.getDictSort() + 1);
        }

        if (StringUtils.isBlank(bizDictPO.getDictValue())) {
            String dictValue = ChineseCharacterUtil.convertHanZi2PinyinFull(bizDictPO.getDictLabel());
            Integer count = lambdaQuery().likeRight(BizDictPO::getDictValue, dictValue).count();
            if (count == 0) {
                bizDictPO.setDictValue(dictValue);
            } else {
                bizDictPO.setDictValue(dictValue + count);
            }
        }
        boolean result = save(bizDictPO);
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }

    @Override
    public BaseResult updateBizDict(BizDictVO vo) {
        BizDictPO bizDictPO = BizDictMsMapper.INSTANCE.vo2po(vo);
        boolean result = updateById(bizDictPO);
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }

    @Override
    public LambdaQueryChainWrapper<BizDictPO> onSelectWhere(BizDictVO vo) {
        LambdaQueryChainWrapper<BizDictPO> queryWrapper = lambdaQuery().orderByDesc(BizDictPO::getId);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), BizDictPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDictSort()), BizDictPO::getDictSort, vo.getDictSort());
        queryWrapper.like(StringUtils.isNotBlank(vo.getDictLabel()), BizDictPO::getDictLabel, vo.getDictLabel());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDictValue()), BizDictPO::getDictValue, vo.getDictValue());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDictType()), BizDictPO::getDictType, vo.getDictType());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getStatus()), BizDictPO::getStatus, vo.getStatus());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getGroupId()), BizDictPO::getGroupId, vo.getGroupId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), BizDictPO::getCanteenId, vo.getCanteenId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getBeginTime()), BizDictPO::getBeginTime, vo.getBeginTime());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getEndTime()), BizDictPO::getEndTime, vo.getEndTime());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUsed()), BizDictPO::getUsed, vo.getUsed());
        return queryWrapper;
    }

}
