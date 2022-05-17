package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.enums.AnonymousEnum;
import com.diandong.mapper.OpinionFeedbackMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.OpinionFeedbackMpService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpinionFeedbackMpServiceImpl extends CommonServiceImpl<OpinionFeedbackMapper, OpinionFeedbackPO>
        implements OpinionFeedbackMpService {

    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private ISysUserService userService;



    @Override
    public void resetOpinionFeedBack(List<OpinionFeedbackPO> list) {

        list.forEach(opinionFeedbackPO -> {
            if (opinionFeedbackPO.getAnonymous() == AnonymousEnum.ANONYMOUS_YES.value()) {
//                如果匿名则修改其创建人姓名
                opinionFeedbackPO.setCreateName("**");
                opinionFeedbackPO.setPhone("***********");

            } else {
                SysUser user = userService.selectUserById(opinionFeedbackPO.getCreateBy());
                opinionFeedbackPO.setCreateName(user.getUserName());
                opinionFeedbackPO.setPhone(user.getPhonenumber());
            }
        });
    }

}
