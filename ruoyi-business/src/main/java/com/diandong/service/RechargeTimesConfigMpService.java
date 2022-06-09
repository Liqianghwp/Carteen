package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.RechargeTimesConfigPO;
import com.diandong.domain.vo.RechargeTimesConfigVO;

/**
 * 充值次数设置Service接口类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
public interface RechargeTimesConfigMpService extends CommonService<RechargeTimesConfigPO> {

    /**
     * 保存充值次数设置
     *
     * @return
     */
    Boolean saveRechargeTimesConfig(RechargeTimesConfigVO vo);

}
