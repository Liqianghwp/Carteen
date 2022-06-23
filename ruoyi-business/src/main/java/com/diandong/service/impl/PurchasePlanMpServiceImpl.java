package com.diandong.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.CanteenPurchaseState;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.dto.PurchasePlanDTO;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.PurchasePlanDetailVO;
import com.diandong.domain.vo.PurchasePlanVO;
import com.diandong.mapper.PurchasePlanMapper;
import com.diandong.mapstruct.PurchasePlanDetailMsMapper;
import com.diandong.mapstruct.PurchasePlanMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.BizIdUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购计划单service实现类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchasePlanMpServiceImpl extends CommonServiceImpl<PurchasePlanMapper, PurchasePlanPO>
        implements PurchasePlanMpService {

    @Resource
    private PurchasePlanDetailMpService purchasePlanDetailMpService;
    @Resource
    private RecipeMpService recipeMpService;
    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private DishesRawMaterialMpService dishesRawMaterialMpService;
    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private RawMaterialMpService rawMaterialMpService;
    @Resource
    private BizIdUtil bizIdUtil;
    @Resource
    private BizDictMpService dictMpService;
    @Resource
    private ReviewListMpService reviewListMpService;
    @Resource
    private PurchasingMpService purchasingMpService;


    @Override
    public void createPurchasePlan(String applyId, CanteenPurchasePO canteenPurchase) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        PurchasePlanPO purchasePlan = new PurchasePlanPO();

        String shoppingListNo = bizIdUtil.getShoppingListNo();
        Long canteenId = SecurityUtils.getCanteenId();

        purchasePlan.setPlanId(shoppingListNo);
        purchasePlan.setApplyId(applyId);
        purchasePlan.setCanteenId(canteenId);
        CanteenPO canteen = canteenMpService.getById(canteenId);
        purchasePlan.setRecipeDate(canteenPurchase.getRecipeStartDate().format(dateTimeFormatter) + "-" + canteenPurchase.getRecipeEndDate().format(dateTimeFormatter));
        purchasePlan.setCanteenName(canteen.getCanteenName());
        purchasePlan.setState(CanteenPurchaseState.UN_SUBMIT);

        save(purchasePlan);

//        查询食谱信息
        List<String> recipeDateList = Arrays.asList(canteenPurchase.getValidDate().split(","));
//        查询出对应的食谱
        List<RecipePO> recipeList = recipeMpService.lambdaQuery()
                .eq(RecipePO::getCanteenId, canteenId)
                .in(RecipePO::getRecipeDate, recipeDateList)
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .list();


        List<Long> recipeIds = recipeList.stream().map(RecipePO::getId).collect(Collectors.toList());
//        食谱对应的详情信息
        List<RecipeDetailPO> recipeDetailList = recipeDetailMpService.lambdaQuery()
                .in(RecipeDetailPO::getRecipeId, recipeIds)
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .list();

        Map<Long, List<RecipeDetailPO>> dishesMap = recipeDetailList.stream().collect(Collectors.groupingBy(RecipeDetailPO::getDishesId));


        List<DishesRawMaterialDTO> dishesRawMaterialDTOList = new ArrayList<>();

        for (Map.Entry<Long, List<RecipeDetailPO>> entry : dishesMap.entrySet()) {
//            菜品id
            Long dishesId = entry.getKey();
//            菜品数量
            int sum = entry.getValue().stream().mapToInt(RecipeDetailPO::getNumber).sum();
//            查询菜品原材料信息
            List<DishesRawMaterialPO> dishesRawMaterialList = dishesRawMaterialMpService.lambdaQuery()
                    .eq(DishesRawMaterialPO::getDishesId, dishesId)
                    .eq(DishesRawMaterialPO::getDelFlag, Constants.DEL_NO)
                    .list();
//            原材料信息处理
            if (CollectionUtil.isNotEmpty(dishesRawMaterialList)) {

                for (DishesRawMaterialPO dishesRawMaterialPO : dishesRawMaterialList) {
                    DishesRawMaterialDTO dishesRawMaterialDTO = new DishesRawMaterialDTO();
                    dishesRawMaterialDTO.setRawMaterialId(dishesRawMaterialPO.getRawMaterialId());
                    dishesRawMaterialDTO.setNumber(sum * dishesRawMaterialPO.getNumber());
                    dishesRawMaterialDTOList.add(dishesRawMaterialDTO);
                }
            }
        }
//        食谱原材料信息
        if (CollectionUtil.isNotEmpty(dishesRawMaterialDTOList)) {
            Map<Long, List<DishesRawMaterialDTO>> dishesRawMaterialMap = dishesRawMaterialDTOList.stream().collect(Collectors.groupingBy(DishesRawMaterialDTO::getRawMaterialId));
            Map<Long, Double> materialMap = new HashMap<>();

            for (Map.Entry<Long, List<DishesRawMaterialDTO>> entry : dishesRawMaterialMap.entrySet()) {
                double sum = entry.getValue().stream().mapToDouble(DishesRawMaterialDTO::getNumber).sum();
                materialMap.put(entry.getKey(), sum);
            }

            List<PurchasePlanDetailPO> purchasePlanDetailList = new ArrayList<>();

            for (Map.Entry<Long, Double> entry : materialMap.entrySet()) {
                RawMaterialPO rawMaterial = rawMaterialMpService.getById(entry.getKey());

                PurchasePlanDetailPO purchasePlanDetail = new PurchasePlanDetailPO();

                PurchasePlanDetailPO lastTimeInquiry = purchasePlanDetailMpService.lambdaQuery()
                        .eq(PurchasePlanDetailPO::getCanteenId, canteenId)
                        .eq(PurchasePlanDetailPO::getRawMaterialId, entry.getKey())
                        .eq(PurchasePlanDetailPO::getDelFlag, Constants.DEL_NO)
                        .orderByDesc(PurchasePlanDetailPO::getId)
                        .last(Constants.limit).one();

                purchasePlanDetail.setCanteenId(canteenId);
                purchasePlanDetail.setPlanId(shoppingListNo);
                purchasePlanDetail.setCategoryId(rawMaterial.getCategoryId());
                purchasePlanDetail.setCategoryName(rawMaterial.getCategoryName());
                purchasePlanDetail.setRawMaterialId(rawMaterial.getId());
                purchasePlanDetail.setRawMaterialName(rawMaterial.getRawMaterialName());
                purchasePlanDetail.setUnitId(rawMaterial.getUnitId());
                purchasePlanDetail.setUnitName(rawMaterial.getUnitName());
                purchasePlanDetail.setNumber(entry.getValue());
                purchasePlanDetail.setLastPurchase(Objects.nonNull(lastTimeInquiry) ? lastTimeInquiry.getCurrentPrice() : null);

                purchasePlanDetailList.add(purchasePlanDetail);
            }
//            保存采购计划单详情
            if (CollectionUtil.isNotEmpty(purchasePlanDetailList)) {
                purchasePlanDetailMpService.saveBatch(purchasePlanDetailList);
            }
        }
    }

    @Override
    public BaseResult findPurchasePlan(Long id) {
        PurchasePlanPO purchasePlan = getById(id);

        PurchasePlanDTO purchasePlanDTO = PurchasePlanMsMapper.INSTANCE.po2dto(purchasePlan);
        List<PurchasePlanDetailPO> list = purchasePlanDetailMpService.lambdaQuery()
                .eq(PurchasePlanDetailPO::getPlanId, purchasePlan.getPlanId())
                .eq(PurchasePlanDetailPO::getDelFlag, Constants.DEL_NO)
                .list();

        if (CollectionUtil.isNotEmpty(list)) {
            purchasePlanDTO.setPurchasePlanDetailList(PurchasePlanDetailMsMapper.INSTANCE.poList2dtoList(list));
        }
        return BaseResult.success(purchasePlanDTO);
    }

    @Override
    public BaseResult savePurchasePlan(PurchasePlanVO vo) {

        Integer state = vo.getState();
//        获取更新的采购计划单详情列表
        List<PurchasePlanDetailVO> purchasePlanDetailVOList = vo.getPurchasePlanDetailVOList();
//        删除对应的
        List<PurchasePlanDetailVO> collect = purchasePlanDetailVOList.stream().filter(purchasePlanDetailVO -> Objects.nonNull(purchasePlanDetailVO.getId())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(collect)) {
            List<Long> notRemoveIds = collect.stream().map(PurchasePlanDetailVO::getId).collect(Collectors.toList());
            List<PurchasePlanDetailPO> planDetailPOList = purchasePlanDetailMpService.lambdaQuery()
                    .notIn(PurchasePlanDetailPO::getId, notRemoveIds)
                    .list();
            List<Long> removeIds = planDetailPOList.stream().map(PurchasePlanDetailPO::getId).collect(Collectors.toList());
            purchasePlanDetailMpService.removeByIds(removeIds);
        } else {
            List<PurchasePlanDetailPO> list = purchasePlanDetailMpService.lambdaQuery()
                    .eq(PurchasePlanDetailPO::getPlanId, vo.getPlanId())
                    .list();
//            删除对应的数据
            List<Long> collect1 = list.stream().map(PurchasePlanDetailPO::getId).collect(Collectors.toList());
            purchasePlanDetailMpService.removeByIds(collect1);
        }
        List<PurchasePlanDetailPO> purchasePlanDetailPOList = new ArrayList<>();

//            仅保存
//            保存数据
        for (PurchasePlanDetailVO purchasePlanDetailVO : purchasePlanDetailVOList) {
            PurchasePlanDetailPO purchasePlanDetailPO;
            if (Objects.isNull(purchasePlanDetailVO.getId())) {
                purchasePlanDetailPO = PurchasePlanDetailMsMapper.INSTANCE.vo2po(purchasePlanDetailVO);

                purchasePlanDetailPO.setCanteenId(SecurityUtils.getCanteenId());
                purchasePlanDetailPO.setPlanId(vo.getPlanId());

                if (StringUtils.isBlank(purchasePlanDetailVO.getCategoryName())) {
                    BizDictPO category = dictMpService.getById(purchasePlanDetailVO.getCategoryId());
                    purchasePlanDetailPO.setCategoryName(category.getDictLabel());
                }
                if (StringUtils.isBlank(purchasePlanDetailVO.getRawMaterialName())) {
                    RawMaterialPO rawMaterialPO = rawMaterialMpService.getById(purchasePlanDetailVO.getRawMaterialId());
                    purchasePlanDetailPO.setRawMaterialName(rawMaterialPO.getRawMaterialName());
                }
                if (StringUtils.isBlank(purchasePlanDetailVO.getUnitName())) {
                    BizDictPO unit = dictMpService.getById(purchasePlanDetailVO.getUnitId());
                    purchasePlanDetailPO.setUnitName(unit.getDictLabel());
                }
            } else {
                purchasePlanDetailPO = purchasePlanDetailMpService.getById(purchasePlanDetailVO.getId());
                purchasePlanDetailPO.setCurrentPrice(purchasePlanDetailVO.getCurrentPrice());
            }

            PurchasePlanDetailPO lastPurchasePlanDetail = purchasePlanDetailMpService.lambdaQuery()
                    .eq(PurchasePlanDetailPO::getPlanId, purchasePlanDetailVO.getPlanId())
                    .eq(PurchasePlanDetailPO::getRawMaterialId, purchasePlanDetailVO.getRawMaterialId())
                    .eq(PurchasePlanDetailPO::getDelFlag, Constants.DEL_NO)
                    .notIn(Objects.nonNull(purchasePlanDetailVO.getId()), PurchasePlanDetailPO::getId, purchasePlanDetailVO.getId())
                    .orderByDesc(PurchasePlanDetailPO::getId).last(Constants.limit).one();

            purchasePlanDetailPO.setLastPurchase(Objects.nonNull(lastPurchasePlanDetail) ? lastPurchasePlanDetail.getLastPurchase() : null);
            purchasePlanDetailPOList.add(purchasePlanDetailPO);
        }
        purchasePlanDetailMpService.saveOrUpdateBatch(purchasePlanDetailPOList);


//        计算总金额
        PurchasePlanPO purchasePlan = getById(vo.getId());
        BigDecimal total = BigDecimal.ZERO;

        for (PurchasePlanDetailPO purchasePlanDetailPO : purchasePlanDetailPOList) {
            BigDecimal subTotal = purchasePlanDetailPO.getCurrentPrice().multiply(BigDecimal.valueOf(purchasePlanDetailPO.getNumber()));
            total = total.add(subTotal);
        }
        purchasePlan.setTotal(total);

        if (CanteenPurchaseState.SUBMIT == state) {

//            检查是否符合提审要求
            List<PurchasePlanDetailPO> errorList = purchasePlanDetailPOList.stream().filter(purchasePlanDetailPO -> Objects.isNull(purchasePlanDetailPO.getCurrentPrice())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(errorList)) {
                throw new ServiceException("价格未填写完，不能提审");
            }

            purchasePlan.setState(vo.getState());
//            提交审核
//            留存记录
            reviewListMpService.purchasePlanSubmit(purchasePlan);
        }

        updateById(purchasePlan);
        return BaseResult.success();
    }

    @Override
    public BaseResult audit(PurchasePlanVO vo) {
        PurchasePlanPO purchasePlan = getById(vo.getId());

//        审核
        reviewListMpService.purchasePlanAudit(purchasePlan);

//        根据审核状态处理其他业务
        Integer state = vo.getState();
        if (CanteenPurchaseState.AUDIT_YES == state) {
//            审核成功  业务处理
            purchasingMpService.createPurchasing(purchasePlan);
        } else if (CanteenPurchaseState.AUDIT_NO == state) {
//            审核失败  业务处理

        } else if (CanteenPurchaseState.REVISE_OPINION == state) {
//            修改意见  业务处理


        }
        purchasePlan.setState(state);
        updateById(purchasePlan);
        return BaseResult.success();
    }
}
