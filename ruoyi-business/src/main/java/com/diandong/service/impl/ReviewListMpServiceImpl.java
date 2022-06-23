package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.ApplyStepTypeConstant;
import com.diandong.constant.AuditTypeConstant;
import com.diandong.constant.CanteenPurchaseState;
import com.diandong.constant.Constants;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.po.PurchasePlanPO;
import com.diandong.domain.po.ReviewListPO;
import com.diandong.enums.RoleEnum;
import com.diandong.mapper.ReviewListMapper;
import com.diandong.service.ReviewListMpService;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 审核列service实现类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReviewListMpServiceImpl extends CommonServiceImpl<ReviewListMapper, ReviewListPO>
        implements ReviewListMpService {

    @Resource
    private ISysRoleService roleService;

//    private

    /**
     * @param canteenPurchasePO 审核业务功能id
     */
    @Override
    public void canteenProcurementAudit(CanteenPurchasePO canteenPurchasePO) {
//        申请人
        ReviewListPO applicant = resetReviewList(canteenPurchasePO.getApplyId());
        applicant.setBusinessId(canteenPurchasePO.getId());
        SysRole applicantRole = roleService.lambdaQuery()
                .eq(SysRole::getRoleKey, RoleEnum.Canteen_Manager.key())
                .eq(SysRole::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();

        applicant.setRoleId(applicantRole.getRoleId());
        applicant.setRoleName(applicantRole.getRoleName());
        applicant.setType(AuditTypeConstant.CanteenPurchaseType);

//        保存审核列表
        save(applicant);
    }

    @Override
    public void purchasePlanSubmit(PurchasePlanPO purchasePlan) {
        ReviewListPO purchasePlanReview = resetReviewList(purchasePlan.getApplyId());
        purchasePlanReview.setBusinessId(purchasePlan.getId());
        SysRole applicantRole = roleService.lambdaQuery()
                .eq(SysRole::getRoleKey, RoleEnum.Group_Purchasing_Manager.key())
                .eq(SysRole::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();
        purchasePlanReview.setRoleId(applicantRole.getRoleId());
        purchasePlanReview.setRoleName(applicantRole.getRoleName());
        purchasePlanReview.setType(AuditTypeConstant.PurchasePlanType);

//        保存审核列表
        save(purchasePlanReview);
    }

    @Override
    public void purchasePlanAudit(PurchasePlanPO purchasePlan) {

        ReviewListPO lastReviewList = lambdaQuery().eq(ReviewListPO::getApplyId, purchasePlan.getApplyId())
                .eq(ReviewListPO::getType, AuditTypeConstant.PurchasePlanType)
                .orderByDesc(ReviewListPO::getId)
                .last(Constants.limit).one();

        ReviewListPO purchasePlanReview = new ReviewListPO();

        purchasePlanReview.setBusinessId(purchasePlan.getId());
        purchasePlanReview.setApplyId(purchasePlan.getApplyId());
        purchasePlanReview.setApplyStatus(purchasePlan.getState());
        purchasePlanReview.setApplyStepType(ApplyStepTypeConstant.AUDIT);
        purchasePlanReview.setUserId(SecurityUtils.getUserId());
        purchasePlanReview.setUserName(SecurityUtils.getUsername());
        purchasePlanReview.setFinishTime(LocalDateTime.now());

        SysRole applicantRole = roleService.lambdaQuery()
                .eq(SysRole::getRoleKey, RoleEnum.Group_Purchasing_Vice_President.key())
                .eq(SysRole::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();
        purchasePlanReview.setRoleId(applicantRole.getRoleId());
        purchasePlanReview.setRoleName(applicantRole.getRoleName());

        purchasePlanReview.setType(AuditTypeConstant.PurchasePlanType);
        purchasePlanReview.setSort(Objects.nonNull(lastReviewList) ? lastReviewList.getSort() + 1 : 1);



        save(purchasePlanReview);
    }


    private ReviewListPO resetReviewList(String applyId) {
        ReviewListPO purchasePlan = new ReviewListPO();

        purchasePlan.setApplyId(applyId);
        purchasePlan.setApplyStatus(CanteenPurchaseState.AUDIT_YES);
        purchasePlan.setApplyStepType(ApplyStepTypeConstant.INITIATE);
        purchasePlan.setUserId(SecurityUtils.getUserId());
        purchasePlan.setUserName(SecurityUtils.getUsername());
        purchasePlan.setFinishTime(LocalDateTime.now());
        purchasePlan.setSort(1);

        return purchasePlan;
    }
}
