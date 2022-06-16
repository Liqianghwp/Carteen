package com.diandong.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.diandong.configuration.CommonService;
import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.vo.BizDictVO;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 业务字典Service接口类
 *
 * @author YuLiu
 * @date 2022-06-10
 */
public interface BizDictMpService extends CommonService<BizDictPO> {


    /**
     * 分页列表
     * @param vo
     * @return
     */
    BaseResult pageList(BizDictVO vo);

    /**
     * 保存业务字典
     *
     * @param vo
     * @return
     */
    BaseResult saveBizDict(BizDictVO vo);

    /**
     * 修改业务字典
     * @param vo
     * @return
     */
    BaseResult updateBizDict(BizDictVO vo);


    /**
     * 根据条件查询
     * @param vo
     * @return
     */
    LambdaQueryChainWrapper<BizDictPO> onSelectWhere(BizDictVO vo);
}
