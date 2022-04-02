package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.vo.OpinionFeedbackResponseVO;
import com.diandong.domain.vo.OpinionFeedbackVO;
import com.diandong.enums.AnonymousEnum;
import com.diandong.mapper.OpinionFeedbackMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.OpinionFeedbackMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
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
    @Resource
    private ISysUserService userService;

    //    @Override
//    public List<OpinionFeedbackResponseVO> getPcOpinionList(Long canteenId) {
//        return null;
//    }
    @Override
    public List<OpinionFeedbackResponseVO> getPcOpinionList(OpinionFeedbackVO vo) {

        List<Long> canteenIds = new ArrayList<>();
        List<OpinionFeedbackPO> list = getList(vo, canteenIds);

        List<OpinionFeedbackResponseVO> responseList = new ArrayList<>();

        list.forEach(opinionFeedbackPO -> {
            OpinionFeedbackResponseVO responseVO = new OpinionFeedbackResponseVO();
            BeanUtils.copyProperties(opinionFeedbackPO, responseVO);
            if (opinionFeedbackPO.getAnonymous() == AnonymousEnum.ANONYMOUS_YES.value()) {
//                如果匿名则修改其创建人姓名
                responseVO.setCreateName("**");
                responseVO.setPhone("***********");

            } else {

            }

            responseList.add(responseVO);
        });


        return responseList;
    }

    //    @Override
//    public List<OpinionFeedbackResponseVO> getGroupPcOpinionList(Long groupId) {
//
//    }
    @Override
    public List<OpinionFeedbackResponseVO> getGroupPcOpinionList(Long groupId, OpinionFeedbackVO vo) {

        List<OpinionFeedbackResponseVO> responseList = new ArrayList<>();
        if (Objects.isNull(vo.getCanteenId())) {
            List<CanteenPO> list = canteenMpService.lambdaQuery()
                    .eq(CanteenPO::getPId, groupId)
                    .eq(CanteenPO::getDataState, 0)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                List<Long> canteenIds = list.stream().map(CanteenPO::getId).collect(Collectors.toList());


                List<OpinionFeedbackPO> opinionFeedbackPOList = getList(vo, canteenIds);


                opinionFeedbackPOList.forEach(opinionFeedbackPO -> {
                    OpinionFeedbackResponseVO responseVO = new OpinionFeedbackResponseVO();
                    BeanUtils.copyProperties(opinionFeedbackPO, responseVO);
                    if (opinionFeedbackPO.getAnonymous() == AnonymousEnum.ANONYMOUS_YES.value()) {
//                如果匿名则修改其创建人姓名
                        responseVO.setCreateName("**");
                        responseVO.setPhone("***********");

                    } else {
                        SysUser user = userService.selectUserById(opinionFeedbackPO.getCreateBy());
                        responseVO.setPhone(user.getPhonenumber());
                    }
                    responseList.add(responseVO);
                });

            }


        } else {
            List<Long> canteenIds = new ArrayList<>();
            List<OpinionFeedbackPO> opinionFeedbackPOList = getList(vo, canteenIds);


            opinionFeedbackPOList.forEach(opinionFeedbackPO -> {
                OpinionFeedbackResponseVO responseVO = new OpinionFeedbackResponseVO();
                BeanUtils.copyProperties(opinionFeedbackPO, responseVO);
                if (opinionFeedbackPO.getAnonymous() == AnonymousEnum.ANONYMOUS_YES.value()) {
//                如果匿名则修改其创建人姓名
                    responseVO.setCreateName("**");
                    responseVO.setPhone("***********");

                } else {
                    SysUser user = userService.selectUserById(opinionFeedbackPO.getCreateBy());
                    responseVO.setPhone(user.getPhonenumber());
                }
                responseList.add(responseVO);
            });

        }


        return responseList;
    }


    /**
     * 返回数据结果
     *
     * @param vo
     * @return
     */
    private List<OpinionFeedbackPO> getList(OpinionFeedbackVO vo, List<Long> canteenIds) {
        if (CollectionUtils.isEmpty(canteenIds)) {
            canteenIds.add(vo.getCanteenId());
        }

        return lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), OpinionFeedbackPO::getId, vo.getId())
                .in(CollectionUtils.isNotEmpty(canteenIds), OpinionFeedbackPO::getCanteenId, canteenIds)
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), OpinionFeedbackPO::getCanteenName, vo.getCanteenName())
                .eq(ObjectUtils.isNotEmpty(vo.getOpinionId()), OpinionFeedbackPO::getOpinionId, vo.getOpinionId())
                .eq(StringUtils.isNotBlank(vo.getOpinionType()), OpinionFeedbackPO::getOpinionType, vo.getOpinionType())
                .eq(StringUtils.isNotBlank(vo.getOpinionContent()), OpinionFeedbackPO::getOpinionContent, vo.getOpinionContent())
                .eq(StringUtils.isNotBlank(vo.getOpinionPicture()), OpinionFeedbackPO::getOpinionPicture, vo.getOpinionPicture())
                .eq(StringUtils.isNotBlank(vo.getProcessInformation()), OpinionFeedbackPO::getProcessInformation, vo.getProcessInformation())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), OpinionFeedbackPO::getStatus, vo.getStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getAnonymous()), OpinionFeedbackPO::getAnonymous, vo.getAnonymous())
                .eq(ObjectUtils.isNotEmpty(vo.getProcessTime()), OpinionFeedbackPO::getProcessTime, vo.getProcessTime())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), OpinionFeedbackPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), OpinionFeedbackPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), OpinionFeedbackPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), OpinionFeedbackPO::getUpdateName, vo.getUpdateName())
                .list();
    }
}
