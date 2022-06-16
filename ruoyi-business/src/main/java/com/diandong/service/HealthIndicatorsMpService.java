package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.domain.vo.HealthIndicatorsVO;
import com.ruoyi.common.core.domain.BaseResult;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface HealthIndicatorsMpService extends CommonService<HealthIndicatorsPO> {

    /**
     * 保存健康指标
     *
     * @param voList
     * @return
     */
    BaseResult saveList(List<HealthIndicatorsVO> voList);


}
