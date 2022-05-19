package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.IngredientsDetailPO;

/**
 * 配料管理详情Service接口类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
public interface IngredientsDetailMpService extends CommonService<IngredientsDetailPO> {

    public Long echoMessage(long id);
}
