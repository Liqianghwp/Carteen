package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.configuration.Insert;
import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.domain.vo.HealthIndicatorsVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

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
    BaseResult saveList(LoginUser loginUser, List<HealthIndicatorsVO> voList);


}
