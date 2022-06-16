package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.domain.vo.HealthIndicatorsVO;
import com.diandong.mapper.HealthIndicatorsMapper;
import com.diandong.mapstruct.HealthIndicatorsMsMapper;
import com.diandong.service.HealthIndicatorsMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HealthIndicatorsMpServiceImpl extends CommonServiceImpl<HealthIndicatorsMapper, HealthIndicatorsPO>
        implements HealthIndicatorsMpService {


    @Override
    public BaseResult saveList(List<HealthIndicatorsVO> voList) {

        List<HealthIndicatorsPO> poList = new ArrayList<>();

        voList.forEach(healthIndicatorsVO -> {
            HealthIndicatorsPO po = HealthIndicatorsMsMapper.INSTANCE.vo2po(healthIndicatorsVO);
            po.setUserId(SecurityUtils.getUserId());
            poList.add(po);
        });

        boolean result = saveOrUpdateBatch(poList);

        if (result) {
            return BaseResult.successMsg("添加成功");
        } else {
            return BaseResult.error("添加失败");
        }

    }


}
