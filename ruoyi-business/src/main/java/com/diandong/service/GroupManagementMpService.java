package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.vo.GroupManagementVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface GroupManagementMpService extends CommonService<GroupManagementPO> {

    /**
     * 添加集团
     *
     * @param vo 集团信息
     * @return  {@link Boolean}
     */
    Boolean saveGroupManagement(GroupManagementVO vo);

}
