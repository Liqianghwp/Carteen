package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.ApplyStepTypeConstant;
import com.diandong.constant.CanteenPurchaseState;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.CanteenPurchaseDTO;
import com.diandong.domain.dto.RecipeDTO;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.CanteenPurchaseVO;
import com.diandong.enums.RoleEnum;
import com.diandong.mapper.CanteenPurchaseMapper;
import com.diandong.mapstruct.CanteenPurchaseMsMapper;
import com.diandong.mapstruct.RecipeDetailMsMapper;
import com.diandong.mapstruct.RecipeMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.BizIdUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 食堂采购service实现类
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CanteenPurchaseMpServiceImpl extends CommonServiceImpl<CanteenPurchaseMapper, CanteenPurchasePO>
        implements CanteenPurchaseMpService {
    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private RecipeMpService recipeMpService;
    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private BizIdUtil bizIdUtil;
    @Resource
    private ReviewListMpService reviewListMpService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private PurchasePlanMpService purchasePlanMpService;


    @Override
    public BaseResult getCanteenPurchase(Long id) {
        CanteenPurchasePO canteenPurchase = getById(id);

        CanteenPurchaseDTO canteenPurchaseDTO = CanteenPurchaseMsMapper.INSTANCE.po2dto(canteenPurchase);


        List<RecipeDTO> recipeList = new ArrayList<>();
        List<String> validDateList = Arrays.asList(canteenPurchaseDTO.getValidDate().split(","));
        for (String recipeDate : validDateList) {
            RecipePO recipe = recipeMpService.lambdaQuery()
                    .eq(RecipePO::getRecipeDate, recipeDate)
                    .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();

            if (Objects.nonNull(recipe)) {
                RecipeDTO recipeDTO = RecipeMsMapper.INSTANCE.po2dto(recipe);

                List<RecipeDetailPO> list = recipeDetailMpService.lambdaQuery()
                        .eq(RecipeDetailPO::getRecipeId, recipe.getId())
                        .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                        .list();

                recipeDTO.setRecipeDetailList(CollectionUtils.isNotEmpty(list) ? RecipeDetailMsMapper.INSTANCE.poList2dtoList(list) : null);

                recipeList.add(recipeDTO);
            }
        }

        canteenPurchaseDTO.setRecipeList(recipeList);
        return BaseResult.success(canteenPurchaseDTO);
    }

    @Override
    public BaseResult saveCanteenPurchase(CanteenPurchaseVO vo) {

        CanteenPO canteen = canteenMpService.getById(SecurityUtils.getCanteenId());
        if (Objects.isNull(canteen)) {
            return BaseResult.error("不能添加食谱采购");
        }

//        开始采购日期和结束日期判断
        LocalDate recipeStartDate = vo.getRecipeStartDate();
        LocalDate recipeEndDate = vo.getRecipeEndDate();
        if (recipeStartDate.compareTo(recipeEndDate) == 0) {
            Integer count = lambdaQuery()
                    .ge(CanteenPurchasePO::getRecipeStartDate, recipeStartDate)
                    .le(CanteenPurchasePO::getRecipeEndDate, recipeEndDate)
                    .eq(CanteenPurchasePO::getDelFlag, Constants.DEL_NO)
                    .count();
            if (count > 0) {
                return BaseResult.error("食谱已经添加采购");
            }
        } else if (recipeStartDate.compareTo(recipeEndDate) > 0) {
            return BaseResult.error("日期传入有误");
        }

//        判断
        String validDate = null;
        List<String> dayList = new ArrayList<>();
        while (recipeStartDate.isBefore(recipeEndDate) || recipeStartDate.isEqual(recipeEndDate)) {
            Integer count = lambdaQuery()
                    .like(CanteenPurchasePO::getValidDate, recipeStartDate.format(DateTimeFormatter.ISO_DATE))
                    .eq(CanteenPurchasePO::getDelFlag, Constants.DEL_NO)
                    .notIn(Objects.nonNull(vo.getId()), CanteenPurchasePO::getId, vo.getId())
                    .count();
            if (count == 0) {
                dayList.add(recipeStartDate.format(DateTimeFormatter.ISO_DATE));
            }

            recipeStartDate = recipeStartDate.plusDays(1L);
        }
//        判断是否已经被添加采购
        if (CollectionUtils.isEmpty(dayList)) {
            return BaseResult.error("食谱已经添加采购");
        }

//        保存更新数据
        validDate = dayList.stream().collect(Collectors.joining(","));
        CanteenPurchasePO canteenPurchasePO = CanteenPurchaseMsMapper.INSTANCE.vo2po(vo);
        canteenPurchasePO.setCanteenId(canteen.getId());
        canteenPurchasePO.setCanteenName(canteen.getCanteenName());
        canteenPurchasePO.setValidDate(validDate);
        canteenPurchasePO.setDays(dayList.size());

        Integer state = vo.getState();

        if (CanteenPurchaseState.SUBMIT == state) {
            if (StringUtils.isBlank(canteenPurchasePO.getApplyId())) {
                String applyId = bizIdUtil.getApplyId();
                canteenPurchasePO.setApplyId(applyId);
            }
            canteenPurchasePO.setSubmitTime(LocalDateTime.now());
        }
        saveOrUpdate(canteenPurchasePO);
        if (CanteenPurchaseState.SUBMIT == state) {
            //            添加审核列表
            reviewListMpService.canteenProcurementAudit(canteenPurchasePO);
        }
        return BaseResult.success();
    }

    @Override
    public BaseResult audit(CanteenPurchaseVO vo) {

        CanteenPurchasePO canteenPurchase = getById(vo.getId());

        if (CanteenPurchaseState.SUBMIT != canteenPurchase.getState()) {
            return BaseResult.error("改订单无法被审核");
        }

        String applyId = canteenPurchase.getApplyId();

//        上一个人的审核信息
        ReviewListPO previousReviewer = reviewListMpService.lambdaQuery()
                .eq(ReviewListPO::getApplyId, applyId)
                .orderByDesc(ReviewListPO::getCreateTime)
                .last(Constants.limit).one();

        ReviewListPO currentReviewer = new ReviewListPO();
        currentReviewer.setBusinessId(canteenPurchase.getId());
        currentReviewer.setApplyId(canteenPurchase.getApplyId());
        currentReviewer.setApplyStatus(vo.getState());
        currentReviewer.setApplyStepType(ApplyStepTypeConstant.AUDIT);
        currentReviewer.setUserId(SecurityUtils.getUserId());
        currentReviewer.setUserName(SecurityUtils.getUsername());

        SysRole sysRole = roleService.selectRoleById(previousReviewer.getRoleId());

        switch (RoleEnum.getRoleEnum(sysRole.getRoleKey())) {
            case Canteen_Manager:
                sysRole = roleService.lambdaQuery().eq(SysRole::getRoleKey, RoleEnum.Group_Executive_Chef.key()).one();
                break;
            case Group_Executive_Chef:
                sysRole = roleService.lambdaQuery().eq(SysRole::getRoleKey, RoleEnum.Group_Executive_Vice_President.key()).one();
                break;
            default:
                return BaseResult.error("审核失败");
        }

        currentReviewer.setRoleId(sysRole.getRoleId());
        currentReviewer.setRoleName(sysRole.getRoleName());
        currentReviewer.setFinishTime(LocalDateTime.now());
        currentReviewer.setApplyOpinion(vo.getRemark());
        currentReviewer.setSort(previousReviewer.getSort() + 1);
        reviewListMpService.save(currentReviewer);
//            更新食堂采购信息

//        审核通过时
        if (currentReviewer.getSort() == 3 && CanteenPurchaseState.AUDIT_YES == currentReviewer.getApplyStatus()) {
//            生成采购计划单
            purchasePlanMpService.createPurchasePlan(applyId, canteenPurchase);
//            审核通过后更新食堂采购
            canteenPurchase.setState(vo.getState());
            updateById(canteenPurchase);
        }

        return BaseResult.success();


    }
}
