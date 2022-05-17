package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.vo.BusinessConfigVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
public interface BusinessConfigMpService extends CommonService<BusinessConfigPO> {


    /**
     * 根据配置名称查询信息
     *
     * @param configName 配置名称
     * @return {@link BusinessConfigPO}
     */
    BusinessConfigPO searchBusinessConfig(String configName);

    /**
     * 保存&更新
     *
     * @param vo 入参信息
     * @return {@link BaseResult}
     */
    BaseResult saveAndUpdate(BusinessConfigVO vo);

}
