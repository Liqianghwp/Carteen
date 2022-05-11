package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.mapper.CanteenMapper;
import com.diandong.mapstruct.CanteenMsMapper;
import com.diandong.service.CanteenMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CanteenMpServiceImpl extends CommonServiceImpl<CanteenMapper, CanteenPO>
        implements CanteenMpService {

    @Override
    public BaseResult addCanteen(CanteenVO vo, LoginUser loginUser) {


        CanteenPO po = CanteenMsMapper.INSTANCE.vo2po(vo);

//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());

        boolean result = save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }

    }
}
