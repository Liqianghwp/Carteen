package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.vo.CanteenVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface CanteenMpService extends CommonService<CanteenPO> {

    /**
     * 添加食堂信息
     *
     * @return
     */
    BaseResult addCanteen(CanteenVO vo);

}
