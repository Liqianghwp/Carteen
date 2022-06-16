package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.configuration.Insert;
import com.diandong.domain.po.SubsidyRecordsPO;
import com.diandong.domain.vo.SubsidyRecordsVO;
import com.diandong.domain.vo.SysUserVO;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.validation.annotation.Validated;

/**
 * 补贴记录Service接口类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
public interface SubsidyRecordsMpService extends CommonService<SubsidyRecordsPO> {

    /**
     * 补贴获取用户列表
     *
     * @param sysUserVO
     * @return
     */
    BaseResult getUserList(SysUserVO sysUserVO);

    /**
     * 补贴记录保存
     *
     * @param vo 数据信息
     * @return
     */
    BaseResult saveSubsidy(SubsidyRecordsVO vo);


    /**
     * 补贴清零
     * @return
     */
    BaseResult subsidyClear();
}
