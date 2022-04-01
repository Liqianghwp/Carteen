package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.vo.OpinionFeedbackResponseVO;
import com.diandong.enums.AnonymousEnum;
import com.diandong.mapper.OpinionFeedbackMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.OpinionFeedbackMpService;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public List<OpinionFeedbackResponseVO> getPcOpinionList(Long canteenId) {


        List<OpinionFeedbackPO> list = lambdaQuery()
                .eq(OpinionFeedbackPO::getCanteenId, canteenId)
                .eq(OpinionFeedbackPO::getDataState, 0)
                .list();

        List<OpinionFeedbackResponseVO> responseList = new ArrayList<>();

        list.forEach(opinionFeedbackPO -> {
            OpinionFeedbackResponseVO responseVO = new OpinionFeedbackResponseVO();
            BeanUtils.copyProperties(opinionFeedbackPO, responseVO);
            if (opinionFeedbackPO.getAnonymous() == AnonymousEnum.ANONYMOUS_YES.value()) {
//                如果匿名则修改其创建人姓名
                responseVO.setCreateName("**");
                responseVO.setPhone("***********");

            }else {

            }

            responseList.add(responseVO);
        });


        return responseList;
    }

    @Override
    public List<OpinionFeedbackResponseVO> getGroupPcOpinionList(Long groupId) {

        List<CanteenPO> list = canteenMpService.lambdaQuery()
                .eq(CanteenPO::getPId, groupId)
                .eq(CanteenPO::getDataState, 0)
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> canteenIds = list.stream().map(CanteenPO::getId).collect(Collectors.toList());


            List<OpinionFeedbackPO> opinionFeedbackPOList = lambdaQuery()
                    .in(OpinionFeedbackPO::getCanteenId, canteenIds)
                    .eq(OpinionFeedbackPO::getDataState, 0)
                    .list();

            List<OpinionFeedbackResponseVO> responseList = new ArrayList<>();

            opinionFeedbackPOList.forEach(opinionFeedbackPO -> {
                OpinionFeedbackResponseVO responseVO = new OpinionFeedbackResponseVO();
                BeanUtils.copyProperties(opinionFeedbackPO, responseVO);
                if (opinionFeedbackPO.getAnonymous() == AnonymousEnum.ANONYMOUS_YES.value()) {
//                如果匿名则修改其创建人姓名
                    responseVO.setCreateName("**");
                    responseVO.setPhone("***********");

                }
                responseList.add(responseVO);
            });
            return responseList;
        }
        return null;
    }
}
