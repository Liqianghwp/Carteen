package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.PhysicalCardPO;
import com.diandong.domain.po.SubsidyRecordsPO;
import com.diandong.domain.po.UserAmountPO;
import com.diandong.domain.vo.SubsidyRecordsVO;
import com.diandong.domain.vo.SysUserVO;
import com.diandong.mapper.SubsidyRecordsMapper;
import com.diandong.mapstruct.SubsidyRecordsMsMapper;
import com.diandong.service.PhysicalCardMpService;
import com.diandong.service.SubsidyRecordsMpService;
import com.diandong.service.UserAmountMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 补贴记录service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubsidyRecordsMpServiceImpl extends CommonServiceImpl<SubsidyRecordsMapper, SubsidyRecordsPO>
        implements SubsidyRecordsMpService {

    @Resource
    private ISysUserService userService;
    @Resource
    private PhysicalCardMpService physicalCardMpService;
    @Resource
    private UserAmountMpService userAmountMpService;

    @Override
    public BaseResult getUserList(SysUserVO sysUserVO) {

        Page<SysUser> page = onSelectWhere(sysUserVO).page(new Page(sysUserVO.getPageNum(), sysUserVO.getPageSize()));

        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            List<SysUser> records = page.getRecords();

            List<Long> collect = records.stream().map(SysUser::getUserId).collect(Collectors.toList());

            List<PhysicalCardPO> list = physicalCardMpService.lambdaQuery()
                    .in(PhysicalCardPO::getUserId, collect)
                    .eq(PhysicalCardPO::getDelFlag, Constants.DEL_NO)
                    .list();
//            重新设置是否有实体卡
            if (CollectionUtils.isNotEmpty(list)) {
                for (SysUser user : records) {
                    long count = list.stream().filter(physicalCardPO -> physicalCardPO.getUserId() == user.getUserId()).count();
                    user.setHasCard(count > 0 ? Constants.DEFAULT_YES : Constants.DEFAULT_NO);
                }
            } else {
                records.forEach(sysUser -> {
                    sysUser.setHasCard(Constants.DEFAULT_NO);
                });
            }


        }
        return BaseResult.success(page);
    }

    @Override
    public BaseResult saveSubsidy(SubsidyRecordsVO vo) {

        List<String> userIds = Arrays.asList(vo.getUserIds().split(","));

        List<UserAmountPO> list = userAmountMpService.lambdaQuery()
                .in(UserAmountPO::getUserId, userIds)
//                .eq(UserAmountPO::getDelFlag, Constants.DEL_NO)
                .list();

//        保存用户钱包信息
        for (String userId : userIds) {
            UserAmountPO userAmount;
            List<UserAmountPO> collect = list.stream().filter(userAmountPO -> Objects.equals(userAmountPO.getUserId(), userId)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                userAmount = collect.get(0);
                if (Constants.DEFAULT_YES == userAmount.getDelFlag()) {
                    userAmount.setAmount(BigDecimal.ZERO);
                    userAmount.setSubsidy(vo.getAmount());
                    userAmount.setDelFlag(Constants.DEL_NO);
                } else {
                    userAmount.setSubsidy(userAmount.getSubsidy().add(vo.getAmount()));
                }
            } else {
                userAmount = new UserAmountPO();
                userAmount.setUserId(Long.valueOf(userId));
                userAmount.setAmount(BigDecimal.ZERO);
                userAmount.setTimes(0);
                userAmount.setSubsidy(vo.getAmount());
            }
            userAmountMpService.saveOrUpdate(userAmount);
        }

        SubsidyRecordsPO subsidyRecordsPO = SubsidyRecordsMsMapper.INSTANCE.vo2po(vo);

        boolean result = save(subsidyRecordsPO);
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }

    @Override
    public BaseResult subsidyClear() {

        List<SysUser> list = userService.lambdaQuery().eq(SysUser::getCanteenId, SecurityUtils.getCanteenId()).list();
        if (CollectionUtils.isNotEmpty(list)) {
            Boolean result = false;

            List<Long> collect = list.stream().map(SysUser::getUserId).collect(Collectors.toList());

            List<UserAmountPO> userAmountList = userAmountMpService.lambdaQuery().in(UserAmountPO::getUserId, collect).list();

            if (CollectionUtils.isNotEmpty(userAmountList)) {

                List<SubsidyRecordsPO> clearList = new ArrayList<>();

                for (UserAmountPO userAmountPO : userAmountList) {

                    SubsidyRecordsPO subsidyRecord = new SubsidyRecordsPO();

//                    SysUser user = userService.selectUserById(userAmountPO.getUserId());
//
//                    TODO 2022年6月9日18:25:00 这个地方有问题
//                    subsidyRecord.setUserType(Long.valueOf(user.getUserType()));
                    subsidyRecord.setUserIds(userAmountPO.getUserId().toString());
                    subsidyRecord.setAmount(userAmountPO.getSubsidy());
                    subsidyRecord.setType(Constants.SUBSIDY_CLEAR);

                    clearList.add(subsidyRecord);
                }

                result = saveBatch(clearList);
            }


            result = userAmountMpService.lambdaUpdate()
                    .set(UserAmountPO::getSubsidy, BigDecimal.ZERO)
                    .in(UserAmountPO::getUserId, collect)
                    .update();

            if (!result) {
                return BaseResult.error("补贴清零失败");
            }
        }

        return BaseResult.success();
    }


    private LambdaQueryChainWrapper<SysUser> onSelectWhere(SysUserVO vo) {
        LambdaQueryChainWrapper<SysUser> wrapper = userService.lambdaQuery();

        wrapper.eq(StringUtils.isNotEmpty(vo.getUserType()), SysUser::getUserType, vo.getUserType())
                .like(StringUtils.isNotEmpty(vo.getNickName()), SysUser::getNickName, vo.getNickName())
                .like(StringUtils.isNotEmpty(vo.getPhoneNumber()), SysUser::getPhonenumber, vo.getPhoneNumber());
        return wrapper;
    }

}
